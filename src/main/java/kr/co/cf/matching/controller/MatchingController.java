package kr.co.cf.matching.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.cf.matching.dto.MatchingDTO;
import kr.co.cf.matching.service.MatchingService;


@Controller
public class MatchingController {
	
	@Autowired MatchingService matchingService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/matching/list.do")
	public String matchingList(Model model, HttpSession session) {
		
		session.setAttribute("LoginId", "user01");
		
		logger.info("모집글 리스트 불러오기");
		
		ArrayList<MatchingDTO> matchingList = new ArrayList<MatchingDTO>();
		
		matchingList = matchingService.basicList();
		
		model.addAttribute("list", matchingList);
		
		return "matchingList";
	}
	
	@RequestMapping(value = "/matching/detail.go")
	public String matchingDetail(Model model, HttpSession session, @RequestParam String matchingIdx) {
		
		logger.info("모집글 matchingIdx : " + matchingIdx + "번 상세보기" );
		
		MatchingDTO matchingDto = new MatchingDTO();
		
		matchingDto = matchingService.matchingDetail(matchingIdx);
		
		model.addAttribute("dto", matchingDto);
		
		return "matchingDetail";
	}
	
	@RequestMapping(value = "/matching/write.go")
	public String matchingWriteGo(@RequestParam String categoryId,Model model, HttpSession session) {
		
		logger.info("모집글 작성 categoryIdx : " + categoryId);
		
		model.addAttribute("LoginId", session.getAttribute("LoginId"));
		
		logger.info("모집글 작성 LoginId : " + session.getAttribute("LoginId"));
		
		return "matchingWriteForm";
	}
	
	@RequestMapping(value = "/matching/write.do")
	public String matchingWrite(@RequestParam HashMap<String, String> params, HttpSession session) {
		
		
		logger.info("모집글 작성 정보 : " + params);
		
		MatchingDTO matchingDto = new MatchingDTO();
		matchingDto.setCategoryId(params.get("categoryId"));
		matchingDto.setContent(params.get("content"));
		matchingDto.setCourtIdx(Integer.parseInt(params.get("courtIdx")));
		matchingDto.setGameDate(params.get("gameDate"));	
		
		matchingDto.setGamePlay(params.get("gamePlay"));
		matchingDto.setMatchingNum(Integer.parseInt(params.get("matchingNum")));
		matchingDto.setSubject(params.get("subject"));
		matchingDto.setWriterId((String) session.getAttribute("LoginId"));
		
		
		matchingService.matchingWrite(matchingDto);
		
		matchingDto.setGameAppState("확정");
		
		matchingService.game(matchingDto);
		int matchingIdx = matchingDto.getmatchingIdx();
		
		return "redirect:/matching/detail.go?matchingIdx="+matchingIdx;
	}
	
	@RequestMapping(value = "/matching/update.go")
	public String matchingUpdateGo(@RequestParam String matchingIdx, Model model) {
		
		logger.info(matchingIdx + "번 모집글 수정");
		
		MatchingDTO matchingDto = new MatchingDTO();
		
		matchingDto = matchingService.matchingDetail(matchingIdx);
		
		model.addAttribute("dto", matchingDto);
		
		return "matchingUpdateForm";
	}
	
	
	
	@RequestMapping(value = "/matching/delete.do")
	public String matchingDelete(@RequestParam String matchingIdx) {
		
		logger.info(matchingIdx + "번 모집글 삭제");
		
		// game 테이블의 matchingIdx가 일치하는 것을 먼저 삭제 후 matching 테이블에서도 삭제해야됨
		// 그러므로 추후 알람을 보내는 기능은 삭제 전에 해야함 
		
		// game 테이블의 matchingIdx가 일치하는 user에게 알람 보내기
		
		// 삭제
		matchingService.delete(matchingIdx);
		
		return "redirect:/matching/list.do";
	}
	
}
