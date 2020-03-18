package com.gotravel.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * 个性表(label)
 * **/
@Data
@Document(collection = "label")
public class Label implements Serializable {

	/**
	 *id
	 */
	@Indexed(unique = true)
	private int chi_id;

	/**
	 * 爱好
	 */
	private  List<String> hobby;

	/**
	 * 用户定制
	 */
	private  List<String> customization;

	/**
	 * 景点类型
	 */
	private  List<String> place_type;


}
