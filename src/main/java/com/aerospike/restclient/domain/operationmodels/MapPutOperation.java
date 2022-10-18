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
package com.aerospike.restclient.domain.operationmodels;

import com.aerospike.client.Value;
import com.aerospike.restclient.util.AerospikeAPIConstants;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Store the specified value into the map in the specified bin with the specified key. Equivalent to `Map[key] = value`.",
        externalDocs = @ExternalDocumentation(url = "https://javadoc.io/doc/com.aerospike/aerospike-client/" + AerospikeAPIConstants.AS_CLIENT_VERSION + "/com/aerospike/client/cdt/MapOperation.html")
)
public class MapPutOperation extends MapOperation {

    @Schema(
            description = "The type of operation. It is always " + OperationTypes.MAP_PUT,
            required = true,
            allowableValues = OperationTypes.MAP_PUT
    )
    final public static String type = OperationTypes.MAP_PUT;

    @Schema(required = true)
    private final Object key;

    @Schema(required = true)
    private final Object value;

    private MapPolicy mapPolicy;

    @JsonCreator
    public MapPutOperation(@JsonProperty(value = "binName", required = true) String binName,
                           @JsonProperty(value = "key", required = true) Object key,
                           @JsonProperty(value = "value", required = true) Object value) {
        super(binName);
        this.key = key;
        this.value = value;
    }

    public MapPolicy getMapPolicy() {
        return mapPolicy;
    }

    public void setMapPolicy(MapPolicy mapPolicy) {
        this.mapPolicy = mapPolicy;
    }

    @Override
    public com.aerospike.client.Operation toOperation() {
        com.aerospike.client.cdt.CTX[] asCTX = getASCTX();
        com.aerospike.client.cdt.MapPolicy asMapPolicy;

        if (mapPolicy == null) {
            asMapPolicy = com.aerospike.client.cdt.MapPolicy.Default;
        } else {
            asMapPolicy = mapPolicy.toMapPolicy();
        }

        return com.aerospike.client.cdt.MapOperation.put(asMapPolicy, binName, Value.get(key), Value.get(value), asCTX);
    }
}
