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

import com.miovision.oss.awsbillingtools.elasticsearch.wrapper.ElasticsearchBulkRequest;
import com.miovision.oss.awsbillingtools.elasticsearch.wrapper.ElasticsearchIndexRequest;
import java.io.IOException;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;

/**
 * A Jest implementation of ElasticsearchBulkRequest.
 */
public class JestElasticsearchBulkRequest implements ElasticsearchBulkRequest {
    private final Bulk.Builder builder = new Bulk.Builder();
    private final JestClient jestClient;

    public JestElasticsearchBulkRequest(JestClient jestClient) {
        this.jestClient = jestClient;
    }

    @Override
    public void add(ElasticsearchIndexRequest indexRequest) {
        builder.addAction(((JestElasticsearchIndexRequest)indexRequest).getIndex());
    }

    @Override
    public void execute() {
        try {
            JestResult result = jestClient.execute(builder.build());
            if(!result.isSucceeded()) {
                throw new JestResultException(result);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}