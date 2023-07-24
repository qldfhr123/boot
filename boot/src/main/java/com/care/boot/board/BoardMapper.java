package com.care.boot.board;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardMapper {

	ArrayList<BoardDTO> boardForm(@Param("begin")int begin, @Param("end")int end);

	int count();

	void boardWriteProc(BoardDTO board);

	BoardDTO boardContent(int no);
	
	void incHit(int no);

	String boardDownload(int no);
	
	void boardModifyProc(BoardDTO board);

	void boardDeleteProc(int no);
}














