package com.example.sa.entity.aws;

import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Priority {
	  HIGH(1),
	  DEFAULT(2),
	  LOW(3);

	  private final int displayValue;
	  
	  Priority(int displayValue) {
		    this.displayValue = displayValue;
		  }

		  public int getDisplayValue() {
		    return displayValue;
		  }	  
}
