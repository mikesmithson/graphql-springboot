package com.tutorial.springboot.graphql.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ResultID implements Serializable {

    private static final long serialVersionUID = 1L;

    private Member student;

    private Subject subject;
}
