/*
 * Copyright 2022 Aerospike, Inc.
 *
 * Portions may be licensed to Aerospike, Inc. under one or more contributor
 * license agreements WHICH ARE COMPATIBLE WITH THE APACHE LICENSE, VERSION 2.0.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.aerospike.restclient.domain.querymodels;

import com.aerospike.client.cdt.CTX;
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.IndexCollectionType;
import com.aerospike.restclient.util.AerospikeAPIConstants;
import com.aerospike.restclient.util.annotations.ASRestClientSchemas;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Filter for CDTs that contain a string value. Only allowed on bins which has a secondary index defined.",
        externalDocs = @ExternalDocumentation(url = "https://javadoc.io/doc/com.aerospike/aerospike-client/" + AerospikeAPIConstants.AS_CLIENT_VERSION + "/com/aerospike/client/query/Filter.html")
)
public class QueryContainsStringFilter extends QueryFilter {
    @Schema(
            description = "The type of query filter this object represents. It is always " + AerospikeAPIConstants.QueryFilterTypes.CONTAINS_STRING,
            required = true,
            allowableValues = AerospikeAPIConstants.QueryFilterTypes.CONTAINS_STRING
    )
    final public static String type = AerospikeAPIConstants.QueryFilterTypes.CONTAINS_STRING;

    @Schema(required = true)
    public String value;

    @ASRestClientSchemas.IndexCollectionType
    public IndexCollectionType collectionType = IndexCollectionType.DEFAULT;

    public QueryContainsStringFilter() {
    }

    @Override
    public Filter toFilter() {
        CTX[] asCTX = getCTXArray();
        return Filter.contains(binName, collectionType, value, asCTX);
    }
}



