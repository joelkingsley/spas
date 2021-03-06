package com.joelkingsley.rmkcet.spas.be.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import com.joelkingsley.rmkcet.spas.be.beans.Batch;
import com.joelkingsley.rmkcet.spas.be.beans.Department;
import com.joelkingsley.rmkcet.spas.be.beans.Exam;
import com.joelkingsley.rmkcet.spas.be.beans.ExamType;
import com.joelkingsley.rmkcet.spas.be.beans.Semester;
import com.joelkingsley.rmkcet.spas.be.beans.Subject;
import com.joelkingsley.rmkcet.spas.be.beans.requests.AddExamRequest;
import com.joelkingsley.rmkcet.spas.be.constants.DBConstants;
import com.joelkingsley.rmkcet.spas.be.constants.DBQueries;
import com.joelkingsley.rmkcet.spas.be.constants.ErrorConstants;
import com.joelkingsley.rmkcet.spas.be.utils.AppError;
import com.joelkingsley.rmkcet.spas.be.utils.DBUtils;

public class ExamsDAO {
	public ArrayList<Exam> getAllExams() throws AppError {
		Connection connection = DBUtils.getConnection();
		ArrayList<Exam> exams = new ArrayList<Exam>();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.GET_ALL_EXAMS);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				
				ExamType examType = new ExamType(
						resultSet.getInt(String.format("%s.%s", DBConstants.TABLE_EXAM_TYPES, DBConstants.COL_EXAM_TYPES_EXAM_TYPE_ID)),
						resultSet.getString(String.format("%s.%s", DBConstants.TABLE_EXAM_TYPES, DBConstants.COL_EXAM_TYPES_EXAM_TYPE_NAME)),
						resultSet.getString(String.format("%s.%s", DBConstants.TABLE_EXAM_TYPES, DBConstants.COL_EXAM_TYPES_ABBREVIATION)));
						
				Subject subject = new Subject(
						resultSet.getInt(String.format("%s.%s", DBConstants.TABLE_SUBJECTS, DBConstants.COL_SUBJECTS_SUBJECT_ID)),
						resultSet.getString(String.format("%s.%s", DBConstants.TABLE_SUBJECTS, DBConstants.COL_SUBJECTS_SUBJECT_CODE)),
						resultSet.getString(String.format("%s.%s", DBConstants.TABLE_SUBJECTS, DBConstants.COL_SUBJECTS_SUBJECT_NAME)),
						resultSet.getInt(String.format("%s.%s", DBConstants.TABLE_SUBJECTS, DBConstants.COL_SUBJECTS_CREDIT)));
				
				Batch batch = new Batch(
						resultSet.getInt(String.format("%s.%s", DBConstants.TABLE_BATCHES, DBConstants.COL_BATCHES_BATCH_ID)),
						resultSet.getInt(String.format("%s.%s", DBConstants.TABLE_BATCHES, DBConstants.COL_BATCHES_BATCH_START_YEAR)));
				
				Department department = new Department(
						resultSet.getInt(String.format("%s.%s", DBConstants.TABLE_DEPARTMENTS, DBConstants.COL_DEPARTMENTS_DEPARTMENT_ID)),
						resultSet.getString(String.format("%s.%s", DBConstants.TABLE_DEPARTMENTS, DBConstants.COL_DEPARTMENTS_DEPARTMENT_NAME)),
						resultSet.getString(String.format("%s.%s", DBConstants.TABLE_DEPARTMENTS, DBConstants.COL_DEPARTMENTS_ABBREVIATION)));
				
				Semester semester = new Semester(
						resultSet.getInt(String.format("%s.%s", DBConstants.TABLE_SEMESTERS, DBConstants.COL_SEMESTERS_SEMESTER_ID)),
						resultSet.getInt(String.format("%s.%s", DBConstants.TABLE_SEMESTERS, DBConstants.COL_SEMESTERS_SEMESTER_NUMBER)),
						batch,
						department);
				
				Exam exam = new Exam(
						resultSet.getInt(String.format("%s.%s", DBConstants.TABLE_EXAMS, DBConstants.COL_EXAMS_EXAM_ID)),
						examType,
						subject,
						semester);
				
				
				exams.add(exam);
			}
			return exams;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw new AppError(ErrorConstants.SERVER_ERROR);
		}
	}
	
	public AddExamRequest addExam(AddExamRequest addExamRequest) throws AppError {
		Connection connection = DBUtils.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.ADD_EXAM, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1,  addExamRequest.getExamTypeID());
			preparedStatement.setInt(2, addExamRequest.getSubjectID());
			preparedStatement.setInt(3, addExamRequest.getSemesterID());
			
			int affectedRows = preparedStatement.executeUpdate();

	        if (affectedRows == 0) {
	            throw new AppError(ErrorConstants.EXAM_NOT_CREATED);
	        }

	        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                addExamRequest.setExamID(generatedKeys.getInt(1));
	                return addExamRequest;
	            }
	            else {
	                throw new AppError(ErrorConstants.EXAM_ID_NOT_OBTAINED);
	            }
	        }
			
		} catch (SQLIntegrityConstraintViolationException exception) {
			exception.printStackTrace();
			throw new AppError(ErrorConstants.DUPLICATE_ENTRY, exception);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw new AppError(ErrorConstants.SERVER_ERROR, sqlException);
		} catch (AppError appError) {
			throw appError;
		}
	}

}
