/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Dmytro Dmytrenko, <dmytrenko.d@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT.  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.bitbucket.ddmytrenko.android.retrofit.converter.msgpack;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import org.msgpack.MessagePack;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;
import retrofit.Converter.Factory;

/**
 * @author Dmytro Dmytrenko
 */
public final class MsgPackConverterFactory extends Factory {

    public static MsgPackConverterFactory create() {
        return create(new MessagePack());
    }

    public static MsgPackConverterFactory create(final MessagePack messagePack) {
        return new MsgPackConverterFactory(messagePack);
    }

    private final MessagePack messagePack;

    private MsgPackConverterFactory(final MessagePack messagePack) {
        this.messagePack = messagePack;
    }

    @Override
    public Converter<?, RequestBody> toRequestBody(final Type type,
                                                   final Annotation[] annotations) {

        return new MsgPackRequestBodyConverter<>(messagePack);
    }

    @Override
    public Converter<ResponseBody, ?> fromResponseBody(final Type type,
                                                       final Annotation[] annotations) {

        if (!(type instanceof Class<?>)) {
            return null;
        }

        final Class<?> valueClass = (Class<?>) type;
        return new MsgPackResponseBodyConverter<>(messagePack, valueClass);
    }
}
