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
        description = "Return all map values with the specified value.",
        externalDocs = @ExternalDocumentation(url = "https://javadoc.io/doc/com.aerospike/aerospike-client/" + AerospikeAPIConstants.AS_CLIENT_VERSION + "/com/aerospike/client/cdt/MapOperation.html")
)
public class MapGetByValueOperation extends MapOperation {

    @Schema(
            description = "The type of operation. It is always " + OperationTypes.MAP_GET_BY_VALUE,
            required = true,
            allowableValues = OperationTypes.MAP_GET_BY_VALUE
    )
    final public static String type = OperationTypes.MAP_GET_BY_VALUE;

    @Schema(required = true)
    private final Object value;

    @Schema(required = true)
    private final MapReturnType mapReturnType;

    private boolean inverted;

    @JsonCreator
    public MapGetByValueOperation(@JsonProperty(value = "binName", required = true) String binName,
                                  @JsonProperty(value = "value", required = true) Object value,
                                  @JsonProperty(value = "mapReturnType", required = true) MapReturnType mapReturnType) {
        super(binName);
        this.value = value;
        this.mapReturnType = mapReturnType;
    }

    public boolean isInverted() {
        return inverted;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    @Override
    public com.aerospike.client.Operation toOperation() {
        com.aerospike.client.cdt.CTX[] asCTX = getASCTX();

        return com.aerospike.client.cdt.MapOperation.getByValue(binName, Value.get(value),
                mapReturnType.toMapReturnType(inverted), asCTX);
    }
}
