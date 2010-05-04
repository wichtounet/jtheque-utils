package org.jtheque.utils.io;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.CharBuffer;

/**
 * This class enable to launch an application and to redirect resultStream and err flows to a StringBuilder.
 *
 * @author adiGuba
 * @author Baptiste Wicht
 */
public final class SimpleApplicationConsumer {
    private final StringBuilderOutputStream resultStream = new StringBuilderOutputStream();

    private final PrintStream out = new PrintStream(resultStream, true);
    private final PrintStream err = new PrintStream(resultStream, true);

    private static final int BUF_SIZE = 4196;

    private final ProcessBuilder builder;

    /**
     * Construct a new SimpleApplicationConsumer to launch a simple application with some arguments.
     *
     * @param args The application followed by the arguments to pass to the application.
     */
    public SimpleApplicationConsumer(String... args) {
        super();

        builder = new ProcessBuilder(args);
    }

    /**
     * Consume all the streams of the process. All the streams are redirected to
     * a simple StringBuilder.
     *
     * @throws IOException I/O error
     */
    public void consume() throws IOException {
        final Process process = builder.start();

        try {
            process.getOutputStream().close();

            dump(new InputStreamReader(process.getErrorStream()), err);
            dump(new InputStreamReader(process.getInputStream()), out);

            try {
                process.waitFor();
            } catch (InterruptedException e) {
                IOException ioe = new InterruptedIOException();
                ioe.initCause(e);
                throw ioe;
            }
        } finally {
            process.destroy();
        }
    }

    /**
     * Dump the in stream into the resultStream stream.
     *
     * @param in  input stream.
     * @param out output stream.
     * @throws IOException I/O error
     */
    private static void dump(Reader in, PrintStream out) throws IOException {
        try {
            try {
                Thread current = Thread.currentThread();
                CharBuffer cb = CharBuffer.allocate(BUF_SIZE);


                cb.clear();

                int len = in.read(cb);

                while (len > 0 && !current.isInterrupted()) {
                    cb.position(0).limit(len);
                    out.append(cb);
                    cb.clear();
                    out.flush();

                    if (current.isInterrupted()) {
                        break;
                    }

                    len = in.read(cb);
                }
            } finally {
                FileUtils.close(in);
            }
        } finally {
            FileUtils.close(out);
        }
    }

    /**
     * Return the result of the consumed process.
     *
     * @return The String result of the consumed process.
     */
    public String getResult() {
        return resultStream.getResult().toString();
    }

    /**
     * An output stream to write to a string builder.
     *
     * @author Baptiste Wicht
     */
    private static final class StringBuilderOutputStream extends OutputStream {
        private final StringBuilder result = new StringBuilder(250);

        @Override
        public void write(int b) throws IOException {
            result.append(String.valueOf((char) b));
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            result.append(new String(b, off, len));
        }

        @Override
        public void write(byte[] b) throws IOException{
			write(b, 0, b.length);
		}

        /**
         * Return the result of the stream.
         *
         * @return A StringBuilder containing all the texts of the stream.
         */
        public StringBuilder getResult() {
            return result;
        }
    }
}