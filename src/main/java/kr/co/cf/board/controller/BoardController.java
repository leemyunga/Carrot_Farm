package kr.co.cf.board.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.cf.board.dto.BoardDTO;
import kr.co.cf.board.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired BoardService service;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/flist.ajax", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> falist(@RequestParam String page, @RequestParam String cnt){
		
		return service.falist(Integer.parseInt(page),Integer.parseInt(cnt));
	}
	
	
	@RequestMapping(value = "/freeboardList.do")
	public String flist(Model model) {
		logger.info("flist 불러오기");
		ArrayList<BoardDTO> flist = service.flist();
		logger.info("flist cnt : " + flist.size());
		model.addAttribute("list", flist);
		return "freeboardList";
	}
	
	@RequestMapping(value="/freeboardWrite.go")
	public String fwriteForm() {
		logger.info("글쓰기로 이동");
		return "freeboardWriteForm";
	}
	
	@RequestMapping(value="/freeboardWrite.do", method=RequestMethod.POST)
	public String fwrite(MultipartFile photo, @RequestParam HashMap<String, String> params) {
		logger.info("Params : " + params);
		return service.fwrite(photo, params);
	}
	
	@RequestMapping(value="/freeboardDetail.do")
	public String fdetail(Model model, @RequestParam String bidx) {
		logger.info("fdetail : " + bidx);
		String page = "redirect:/freeboardList.do";
		
		BoardDTO dto = service.fdetail(bidx,"detail");
		if(dto != null) {
			page = "freeboardDetail";
			model.addAttribute("dto", dto);
			logger.info("사진이름" +dto.getPhotoName());
		}
		return page;
	}
	
	@RequestMapping(value = "/freeboardDelete.do")
	public String fdelete(@RequestParam String bidx) {
		service.fdelete(bidx);
		return "redirect:/freeboardList.do";
	}
	
	@RequestMapping(value = "/freeboardUpdate.go")
	public String fupdateForm(Model model, @RequestParam String bidx) {
		logger.info("fupdate : " + bidx);
		String page = "redirect:/freeboardList.do";
		
		BoardDTO dto = service.fdetail(bidx,"freeboardUpdate");
		logger.info("dto 들어갔음? : " + dto);
		if(dto != null) {
			page = "freeboardUpdateForm";
			model.addAttribute("dto", dto);
		}
		return page;
	}
	
	@RequestMapping(value="/freeboardUpdate.do", method=RequestMethod.POST)
	public String fupdate(MultipartFile photo, @RequestParam HashMap<String, String> params) {
		logger.info("Params : " + params);
		return service.fupdate(photo, params);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/nlist.ajax", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> nalist(@RequestParam String page, @RequestParam String cnt){
		
		return service.nalist(Integer.parseInt(page),Integer.parseInt(cnt));
	}
	
	@RequestMapping(value = {"/noticeboardList.do"})
	public String nlist(Model model) {
		logger.info("nlist 불러오기");
		ArrayList<BoardDTO> nlist = service.nlist();
		logger.info("nlist cnt : " + nlist.size());
		model.addAttribute("list", nlist);
		return "noticeboardList";
	}
	
	@RequestMapping(value="/noticeboardWrite.go")
	public String nwriteForm(Model model, HttpSession session) {
		logger.info("글쓰기로 이동");
		model.addAttribute("loginId", session.getAttribute("loginId"));
		return "noticeboardWriteForm";
	}
	
	@RequestMapping(value="/noticeboardWrite.do", method=RequestMethod.POST)
	public String nwrite(MultipartFile photo, @RequestParam HashMap<String, String> params, HttpSession session) {
			logger.info("Params : " + params);
			return service.nwrite(photo, params);
	}
	
	@RequestMapping(value="/noticeboardDetail.do")
	public String ndetail(Model model, @RequestParam String bidx, HttpSession session) {
		logger.info("ndetail : " + bidx);
		String page = "redirect:/noticeboardList.do";
		
		BoardDTO dto = service.ndetail(bidx,"detail");
		if(dto != null) {
			page = "noticeboardDetail";
			model.addAttribute("dto", dto);
			logger.info("사진이름" +dto.getPhotoName());
		}
		return page;
	}
	
	@RequestMapping(value = "/noticeboardDelete.do")
	public String ndelete(@RequestParam String bidx, HttpSession session) {
		service.ndelete(bidx);
		return "redirect:/noticeboardList.do";
	}
	
	@RequestMapping(value = "/noticeboardUpdate.go")
	public String nupdateForm(Model model, @RequestParam String bidx) {
		logger.info("nupdate : " + bidx);
		String page = "redirect:/noticeboardList.do";
		
		BoardDTO dto = service.ndetail(bidx,"noticeboardUpdate");
		logger.info("dto 들어갔음? : " + dto);
		if(dto != null) {
			page = "noticeboardUpdateForm";
			model.addAttribute("dto", dto);
		}
		return page;
	}
	
	@RequestMapping(value="/noticeboardUpdate.do", method=RequestMethod.POST)
	public String nupdate(MultipartFile photo, @RequestParam HashMap<String, String> params, HttpSession session) {
		logger.info("Params : " + params);
		return service.nupdate(photo, params);
	}
	
	@RequestMapping(value="/userRight.ajax")
	@ResponseBody
	public String userRight(HttpSession session){
		String loginId = String.valueOf(session.getAttribute("loginId"));
		logger.info(loginId);
		logger.info("통신성공");
		
		return service.userRight(loginId);
	}
	
	
	
	
	
	
	
	@RequestMapping(value="/ilist.ajax", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> ialist(@RequestParam String page, @RequestParam String cnt){
		
		return service.ialist(Integer.parseInt(page),Integer.parseInt(cnt));
	}
	
	@RequestMapping(value = "/inquiryboardList.do")
	public String ilist(Model model) {
		logger.info("ilist 불러오기");
		ArrayList<BoardDTO> ilist = service.ilist();
		logger.info("ilist cnt : " + ilist.size());
		model.addAttribute("list", ilist);
		return "inquiryboardList";
	}
	
	@RequestMapping(value="/inquiryboardWrite.go")
	public String iwriteForm(Model model, HttpSession session) {
		logger.info("글쓰기로 이동");
		model.addAttribute("loginId", session.getAttribute("loginId"));
		return "inquiryboardWriteForm";
	}
	
	@RequestMapping(value="/inquiryboardWrite.do", method=RequestMethod.POST)
	public String iwrite(MultipartFile photo, @RequestParam HashMap<String, String> params, HttpSession session) {
			logger.info("Params : " + params);
			return service.iwrite(photo, params);
	}
	
	@RequestMapping(value="/inquiryboardDetail.do")
	public String idetail(Model model, @RequestParam String bidx, HttpSession session) {
		logger.info("idetail : " + bidx);
		String page = "redirect:/inquiryboardList.do";
		
		BoardDTO dto = service.idetail(bidx,"detail");
		if(dto != null) {
			page = "inquiryboardDetail";
			model.addAttribute("dto", dto);
			logger.info("사진이름" +dto.getPhotoName());
		}
		return page;
	}
	
	@RequestMapping(value = "/inquiryboardDelete.do")
	public String idelete(@RequestParam String bidx, HttpSession session) {
		service.idelete(bidx);
		return "redirect:/inquiryboardList.do";
	}
	
	@RequestMapping(value = "/inquiryboardUpdate.go")
	public String iupdateForm(Model model, @RequestParam String bidx) {
		logger.info("iupdate : " + bidx);
		String page = "redirect:/inquiryboardList.do";
		
		BoardDTO dto = service.idetail(bidx,"inquiryboardUpdate");
		logger.info("dto 들어갔음? : " + dto);
		if(dto != null) {
			page = "inquiryboardUpdateForm";
			model.addAttribute("dto", dto);
		}
		return page;
	}
	
	@RequestMapping(value="/inquiryboardUpdate.do", method=RequestMethod.POST)
	public String iupdate(MultipartFile photo, @RequestParam HashMap<String, String> params, HttpSession session) {
		logger.info("Params : " + params);
		return service.iupdate(photo, params);
	}
	
	@RequestMapping(value="/iuserRight.ajax")
	@ResponseBody
	public String iuserRight(HttpSession session){
		String loginId = String.valueOf(session.getAttribute("loginId"));
		logger.info(loginId);
		logger.info("통신성공");
		
		return service.iuserRight(loginId);
	}
	
}
