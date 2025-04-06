CREATE TABLE attendance (
    Att_id INT PRIMARY KEY AUTO_INCREMENT,
    Att_stu_id CHAR(8) NOT NULL,
    Att_cou_id CHAR(8) NOT NULL,
    Pre_date DATE,
    Pre_time TIME,
    Lec_hours INT,
    Lec_type CHAR(2),
    Statu CHAR(2) NOT NULL,
    Att_medi_id CHAR(8),
    FOREIGN KEY (Att_stu_id) REFERENCES student(student_id) ON UPDATE CASCADE,
    FOREIGN KEY (Att_cou_id) REFERENCES course(Co_id) ON UPDATE CASCADE,
    FOREIGN KEY (Att_medi_id) REFERENCES medical(medi_id) ON UPDATE CASCADE
);
/*Only first run*/
INSERT INTO attendance (Att_id, Att_stu_id, Att_cou_id, Pre_date, Pre_time, Lec_hours, Lec_type, Statu) VALUES
(1,'TG1301','ICT1233','2025-04-05','14:30:00',2,'t','p');

/*data*/ 
INSERT INTO attendance (Att_stu_id, Att_cou_id, Pre_date, Pre_time, Lec_hours, Lec_type, Statu) VALUES
('TG1301','ICT1233','2025-04-05','14:30:00',2,'t','p'),
('TG1303','ICT1233','2025-04-05','14:30:00',2,'t','p'),
('TG1304','ICT1233','2025-04-05','14:30:00',2,'t','p'),
('TG1305','ICT1233','2025-04-05','14:30:00',2,'t','p'),
('TG1306','ICT1233','2025-04-05','14:30:00',2,'t','p'),
('TG1307','ICT1233','2025-04-05','14:30:00',2,'t','a');

('TG1303','ICT1233',1,1,'t','T01'),
('TG1306','ICT1233',1,1,'t','T01'),
('TG1309','ICT1233',1,1,'t','T01'),
('TG1311','ICT1233',1,1,'t','T01'),
('TG1312','ICT1233',1,1,'t','T01');
