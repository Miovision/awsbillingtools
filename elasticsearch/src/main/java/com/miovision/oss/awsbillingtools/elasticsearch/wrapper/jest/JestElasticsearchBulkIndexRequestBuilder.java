/*
 * The MIT License (MIT)
 *
 * Copyright (c)  2016 the original author or authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.miovision.oss.awsbillingtools.elasticsearch.wrapper.jest;

import com.miovision.oss.awsbillingtools.elasticsearch.wrapper.ElasticsearchIndexRequest;
import com.miovision.oss.awsbillingtools.elasticsearch.wrapper.ElasticsearchIndexRequestBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import java.io.IOException;
import java.util.Map;
import io.searchbox.core.Index;

/**
 * A Jest implementation of ElasticsearchIndexRequestBuilder.
 */
public class JestElasticsearchBulkIndexRequestBuilder implements ElasticsearchIndexRequestBuilder {
    private final String index;
    private final String type;
    private String id;
    private String fieldsJson;

    public JestElasticsearchBulkIndexRequestBuilder(String index, String type) {
        this.index = index;
        this.type = type;
    }

    @Override
    public ElasticsearchIndexRequestBuilder setId(String recordId) {
        this.id = recordId;
        return this;
    }

    @Override
    public ElasticsearchIndexRequestBuilder setSource(Map<String, ?> fields) {
        try {
            this.fieldsJson = XContentFactory.jsonBuilder().map(fields).string();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public ElasticsearchIndexRequest request() {
        final Index.Builder builder = new Index.Builder(fieldsJson)
                .index(index)
                .type(type)
                .id(id);
        return new JestElasticsearchIndexRequest(builder.build());
    }
}
