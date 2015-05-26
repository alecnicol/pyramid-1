package edu.neu.ccs.pyramid.data_formatter.yelp;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

/**
 * Created by Rainicy on 5/1/15.
 */
public class IndexBuilder {

    public static XContentBuilder createBuilder(String reviewId, String body, String label) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();

        builder.startObject();
        builder.field("reviewId", reviewId);
        builder.field("body", body);
        builder.field("label", label);
        builder.endObject();

        return builder;
    }

    public static XContentBuilder createMapping() throws Exception {

        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("document")
                .startObject("properties")
                .startObject("review_id")
                .field("type", "string")
                .field("store", true)
                .field("index", "not_analyzed")
                .endObject()
                .startObject("body")
                .field("type", "string")
                .field("store", true)
                .field("index", "analyzed")
                .field("term_vector", "with_positions")
                .field("analyzer", "my_analyzer")
                .endObject()
                .startObject("label")
                .field("type", "string")
                .field("store", true)
                .field("index", "not_analyzed")
                .endObject()
                .endObject()
                .endObject()
                .endObject();

        return mapping;
    }
}