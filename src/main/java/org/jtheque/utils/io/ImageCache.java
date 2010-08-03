package org.jtheque.utils.io;

import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.ImageUtils;

import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

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

@ThreadSafe
public class ImageCache {
    private final ConcurrentMap<String, Future<SoftImage>> cache;

    public ImageCache() {
        this(25);
    }

    public ImageCache(int capacity) {
        super();

        cache = CollectionUtils.newConcurrentMap(capacity);
    }

    public void invalidate(String path){
        cache.remove(path);
    }

    public ImageIcon getIconFromFile(String path){
        BufferedImage image = getFromFile(path);

        return image == null ? null : new ImageIcon(image);
    }

    public ImageIcon getIcon(String path, InputStream stream) {
        return new ImageIcon(get(path, stream));
    }

    public ImageIcon getIcon(String path, OnDemandStream stream) {
        return new ImageIcon(get(path, stream));
    }

    public BufferedImage getFromFile(String path) {
        File f = new File(path);

        return f.exists() ? get(path, new OnDemandInputStream(f)) : null;
    }

    public BufferedImage get(String path, InputStream stream){
        return get(path, new SimpleStream(stream));
    }

    public BufferedImage get(String path, OnDemandStream stream){
        while (true) {
            Future<SoftImage> f = cache.get(path);

            if (f == null) {
                FutureTask<SoftImage> ft = new FutureTask<SoftImage>(new ImageLoader(stream.get()));

                f = cache.putIfAbsent(path, ft);

                if (f == null) {
                    f = ft;
                    ft.run();
                } else {
                    FileUtils.close(stream.get());
                }
            }

            try {
                SoftImage image = f.get();

                if (image.hasBeenCleared()) {
                    cache.remove(path, f);
                } else {
                    return image.get();
                }
            } catch (CancellationException e) {
                cache.remove(path, f);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                throw launderThrowable(e.getCause());
            }
        }
    }

    private static RuntimeException launderThrowable(Throwable cause) {
        if(cause instanceof RuntimeException){
            return (RuntimeException) cause;
        } else if(cause instanceof Error){
            throw (Error) cause;
        } else {
            throw new IllegalStateException("Not an unchecked exception", cause);
        }
    }

    private static class ImageLoader implements Callable<SoftImage> {
        private final InputStream stream;

        private ImageLoader(InputStream stream) {
            super();

            this.stream = stream;
        }

        @Override
        public SoftImage call() throws Exception {
            return new SoftImage(ImageUtils.openCompatibleImage(stream));
        }
    }
}
