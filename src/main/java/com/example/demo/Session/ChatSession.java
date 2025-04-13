package com.example.demo.Session;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.example.demo.dto.ChatContextDto;

import lombok.Data;

@Data
@Component
@SessionScope
public class ChatSession {

	private List<ChatContextDto> chatList = new ArrayList<>();
	
	public void add(ChatContextDto chatContext) {
		chatList.add(chatContext);
	}
}
