CREATE TABLE attendance (
                            Att_id INT PRIMARY KEY AUTO_INCREMENT,
                            Att_stu_id CHAR(8) NOT NULL,
                            Att_cou_id CHAR(8) NOT NULL,
                            Pre_date DATE,
                            Pre_time TIME,
                            Lec_hours INT,
                            Lec_type ENUM('Lecture', 'Practical'),
                            Status_ ENUM('Present', 'Absent' , 'Medical') DEFAULT 'Absent',
                            Att_medi_id INT,
                            FOREIGN KEY (Att_stu_id) REFERENCES userAccount(user_id) ON UPDATE CASCADE,
                            FOREIGN KEY (Att_cou_id) REFERENCES courseUnit(courseId) ON UPDATE CASCADE,
                            FOREIGN KEY (Att_medi_id) REFERENCES medical(Medi_id) ON UPDATE CASCADE
);
/*Only first run*/
INSERT INTO attendance (Att_id, Att_stu_id, Att_cou_id, Pre_date, Pre_time, Lec_hours, Lec_type, Status_) VALUES
    (1,'TG1301','ICT1233','2025-04-05','14:30:00',2,'Lecture','Present');

/*data*/
INSERT INTO attendance (Att_stu_id, Att_cou_id, Pre_date, Pre_time, Lec_hours, Lec_type, Status_) VALUES
    ('TG1301','ICT1233','2025-04-05','14:30:00',2,'Lecture','Present'),
    ('TG1303','ICT1233','2025-04-05','14:30:00',2,'Lecture','Present'),
    ('TG1304','ICT1233','2025-04-05','14:30:00',2,'Lecture','Present'),
    ('TG1305','ICT1233','2025-04-05','14:30:00',2,'Lecture','Absent'),
    ('TG1306','ICT1233','2025-04-05','14:30:00',2,'Lecture','Present'),
    ('TG1307','ICT1233','2025-04-05','14:30:00',2,'Lecture','Present');

('TG1303','ICT1233',1,1,'t','T01'),
('TG1306','ICT1233',1,1,'t','T01'),
('TG1309','ICT1233',1,1,'t','T01'),
('TG1311','ICT1233',1,1,'t','T01'),
('TG1312','ICT1233',1,1,'t','T01');


CREATE TABLE medical(
                        Medi_id INT PRIMARY KEY AUTO_INCREMENT,
                        Me_stu_id CHAR(8) NOT NULL,
                        Me_cou_id CHAR(8) NOT NULL,
                        Reason VARCHAR(250) NOT NULL,
                        Request_date DATE,
                        Status_ ENUM('rejected', 'pending', 'approved') DEFAULT 'pending',
                        Submitted_date DATE,
                        FOREIGN KEY (Me_stu_id) REFERENCES student(Student_id) ON UPDATE CASCADE,
                        FOREIGN KEY (Me_cou_id) REFERENCES course(Co_id) ON UPDATE CASCADE
);

INSERT INTO medical()VALUES
    ();