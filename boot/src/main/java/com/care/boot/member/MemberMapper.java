package com.care.boot.member;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

	MemberDTO loginProc(String id);

	void registerProc(MemberDTO member);

	ArrayList<MemberDTO> memberInfo(
			@Param("begin")int begin, @Param("end")int end, 
			@Param("select")String select, @Param("search")String search);

	int count(@Param("select")String select, @Param("search")String search);

	int updateProc(MemberDTO member);

	void delete(String id);

	String select(String id);

}
