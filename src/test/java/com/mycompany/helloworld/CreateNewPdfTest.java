/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.helloworld;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zwq19961228
 */
public class CreateNewPdfTest {
    
    public CreateNewPdfTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class CreateNewPdf.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] arg = null;
        CreateNewPdf.main(arg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createPdf method, of class CreateNewPdf.
     */
    @Test
    public void testCreatePdf() throws Exception {
        System.out.println("createPdf");
        String dest = "";
        CreateNewPdf instance = new CreateNewPdf();
        instance.createPdf(dest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of submissionInfo method, of class CreateNewPdf.
     */
    @Test
    public void testSubmissionInfo() throws Exception {
        System.out.println("submissionInfo");
        Table si = null;
        info info = null;
        CreateNewPdf instance = new CreateNewPdf();
        instance.submissionInfo(si, info);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of paragraphComparision method, of class CreateNewPdf.
     */
    @Test
    public void testParagraphComparision() throws Exception {
        System.out.println("paragraphComparision");
        Document document = null;
        info info = null;
        PdfDocument pdf = null;
        CreateNewPdf instance = new CreateNewPdf();
        instance.paragraphComparision(document, info, pdf);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of teacherFeedback method, of class CreateNewPdf.
     */
    @Test
    public void testTeacherFeedback() throws Exception {
        System.out.println("teacherFeedback");
        Document document = null;
        info info = null;
        CreateNewPdf instance = new CreateNewPdf();
        instance.teacherFeedback(document, info);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
