package com.revature.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Planet {
	
	private int id;
	private String name;
	private int ownerId;
}
