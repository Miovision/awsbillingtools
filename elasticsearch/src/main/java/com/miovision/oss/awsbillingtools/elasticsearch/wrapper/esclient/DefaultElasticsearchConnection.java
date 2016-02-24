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

package com.miovision.oss.awsbillingtools.elasticsearch.wrapper.esclient;

import com.miovision.oss.awsbillingtools.elasticsearch.wrapper.ElasticsearchBulkRequest;
import com.miovision.oss.awsbillingtools.elasticsearch.wrapper.ElasticsearchConnection;
import com.miovision.oss.awsbillingtools.elasticsearch.wrapper.ElasticsearchIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.transport.TransportClient;

/**
 * The default implementation of ElasticsearchConnection.
 */
public class DefaultElasticsearchConnection implements ElasticsearchConnection {
    private final TransportClient transportClient;

    public DefaultElasticsearchConnection(TransportClient transportClient) {
        this.transportClient = transportClient;
    }

    @Override
    public void close() throws Exception {
        transportClient.close();
    }

    @Override
    public void deleteIndex(String index) {
        transportClient.admin().indices().delete(new DeleteIndexRequest(index));
    }

    @Override
    public ElasticsearchIndexRequestBuilder prepareIndex(String index, String type) {
        return new DefaultElasticsearchIndexRequestBuilder(transportClient.prepareIndex(index, type));
    }

    @Override
    public ElasticsearchBulkRequest prepareBulk() {
        return new DefaultElasticsearchBulkRequest(transportClient.prepareBulk());
    }

}