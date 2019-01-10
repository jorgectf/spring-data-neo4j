/*
 * Copyright 2011-2019 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.neo4j.repository;

import java.util.UUID;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by markangrish on 24/03/2017.
 */
@NodeEntity
public abstract class Contact {

	@GraphId protected Long id;

	@Index(primary = true, unique = true) private String uuid;

	public Contact() {
		this.uuid = UUID.randomUUID().toString();
	}

	public Long getId() {
		return id;
	}

	public String getUuid() {
		return uuid;
	}
}