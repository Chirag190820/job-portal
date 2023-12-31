package edu.project.jobportal.Job_Portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.project.jobportal.Job_Portal.dao.ApplicantDao;
import edu.project.jobportal.Job_Portal.dao.ResumeDao;
import edu.project.jobportal.Job_Portal.dao.SkillDao;
import edu.project.jobportal.Job_Portal.entity.Applicant;
import edu.project.jobportal.Job_Portal.entity.Resume;
import edu.project.jobportal.Job_Portal.entity.Skill;
import edu.project.jobportal.Job_Portal.exception.ApplicantNotfoundByIdException;
import edu.project.jobportal.Job_Portal.exception.ResumeNotFoundByIdException;
import edu.project.jobportal.Job_Portal.util.responseStructure;


@Service
public class SkillService {

	@Autowired
	private SkillDao skillDao;
	@Autowired
	private ApplicantDao applicantDao;
	@Autowired
	private ResumeDao resumeDao;

	public ResponseEntity<responseStructure<Resume>> saveSkillToApplicant(long applicantId, String[] skills) {
		Applicant applicant = applicantDao.getApplicant(applicantId);
		if (applicant != null) {
			Resume resume = applicant.getResume();
			if (resume != null) {
				/*
				 * - iterate over the String arrays skills that is received check if the skill
				 * is present with matching name with the user, if present do not add to the
				 * user again, or else create an new skill
				 */
				List<Skill> exSkills = resume.getSkills();
				for (String skill : skills) {
					Skill existingskill = skillDao.getSkillByName(skill);
					
					if (existingskill != null) {
						if (!exSkills.contains(existingskill)) {
							exSkills.add(existingskill);
						}
					} else {
						Skill newSkill = new Skill();
						newSkill.setSkillName(skill);
						skillDao.saveSkill(newSkill);
						exSkills.add(newSkill);
					}
				}
				/*
				 * setting the exSkills list to the resume*/
				resume.setSkills(exSkills);
				
				
				resume = resumeDao.saveResume(resume);
				responseStructure<Resume> responseStructure = new responseStructure<>();
				responseStructure.setStatusCode(HttpStatus.CREATED.value());
				responseStructure.setMessage("Skills added successfully!!");
				responseStructure.setData(resume);
				return new ResponseEntity<responseStructure<Resume>>(responseStructure, HttpStatus.CREATED);

			} else {
				throw new ResumeNotFoundByIdException("Failed to add skills!!");
			}
		} else {
			throw new ApplicantNotfoundByIdException("Failed to add skills!!");
		}
	}
}
