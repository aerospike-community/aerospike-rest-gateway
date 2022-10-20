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
package com.aerospike.restclient.domain.ctxmodels;

import com.aerospike.client.Value;
import com.aerospike.restclient.util.AerospikeAPIConstants;
import com.aerospike.restclient.util.deserializers.ObjectDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Lookup list by value",
        externalDocs = @ExternalDocumentation(url = "https://javadoc.io/doc/com.aerospike/aerospike-client/" + AerospikeAPIConstants.AS_CLIENT_VERSION + "/com/aerospike/client/cdt/CTX.html")
)
public class ListValueCTX extends CTX {
    @Schema(
            description = "The type of context this object represents. It is always " + AerospikeAPIConstants.CTX.LIST_VALUE,
            allowableValues = AerospikeAPIConstants.CTX.LIST_VALUE,
            required = true
    )
    @JsonProperty(required = true)
    public final String type = AerospikeAPIConstants.CTX.LIST_VALUE;

    @Schema(required = true)
    @JsonDeserialize(using = ObjectDeserializer.class)
    public Object value;

    ListValueCTX() {
    }

    @Override
    public com.aerospike.client.cdt.CTX toCTX() {
        Value asVal = Value.get(value);
        return com.aerospike.client.cdt.CTX.listValue(asVal);
    }
}

