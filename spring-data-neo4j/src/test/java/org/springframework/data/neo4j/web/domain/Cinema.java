/*
 * Copyright 2011-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.neo4j.web.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.typeconversion.UuidStringConverter;

/**
 * @author Michal Bachman
 * @author Mark Angrish
 */
@NodeEntity
public class Cinema {

	@Id @GeneratedValue private Long id;

	@Convert(UuidStringConverter.class) @Index(unique = true) private UUID uuid;

	private String name;

	@Relationship(direction = Relationship.INCOMING) private Set<User> visited;

	public Cinema() {}

	public Cinema(String name) {
		this.visited = new HashSet<>();
		this.uuid = UUID.randomUUID();
		this.name = name;
	}

	public void addVisitor(User user) {
		visited.add(user);
	}

	public String getName() {
		return name;
	}
}
