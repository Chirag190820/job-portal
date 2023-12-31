package edu.project.jobportal.Job_Portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.project.jobportal.Job_Portal.entity.Resume;
import edu.project.jobportal.Job_Portal.service.SkillService;
import edu.project.jobportal.Job_Portal.util.responseStructure;


@RestController
@RequestMapping("/skills")
public class SkillController {
	
	@Autowired
	private SkillService skillService;
	
	@PostMapping
	public ResponseEntity<responseStructure<Resume>> saveSkillsToApplicant
	(@RequestParam long applicantId, @RequestParam String[] skills){
		return skillService.saveSkillToApplicant(applicantId, skills);
	}
	
	
	
}

 