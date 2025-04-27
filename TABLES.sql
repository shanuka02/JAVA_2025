CREATE TABLE attendance (
                            Att_id INT PRIMARY KEY AUTO_INCREMENT,
                            Att_stu_id CHAR(8) NOT NULL,
                            Att_cou_id CHAR(8) NOT NULL,
                            Pre_date DATE,
                            Pre_time TIME,
                            Lec_hours INT,
                            Lec_type ENUM('Theory', 'Practical'),
                            Status_ ENUM('Present', 'Absent' , 'Medical') DEFAULT 'Absent',
                            Att_medi_id INT,
                            FOREIGN KEY (Att_stu_id) REFERENCES userAccount(user_id),
                            FOREIGN KEY (Att_cou_id) REFERENCES courseUnit(courseId),
                            FOREIGN KEY (Att_medi_id) REFERENCES medical(Medi_id)
);
/*Only first run*/
INSERT INTO attendance (Att_id, Att_stu_id, Att_cou_id, Pre_date, Pre_time, Lec_hours, Lec_type, Status_) VALUES
    (1,'TG1301','ICT2122','2025-04-05','14:30:00',2,'Theory','Present');

/*data*/
INSERT INTO attendance (Att_stu_id, Att_cou_id, Pre_date, Pre_time, Lec_hours, Lec_type, Status_) VALUES
    ('TG1302','ICT2122','2025-04-05','14:30:00',2,'Theory','Present'),
    ('TG1303','ICT2122','2025-04-05','14:30:00',2,'Theory','Present'),
    ('TG1304','ICT2122','2025-04-05','14:30:00',2,'Theory','Absent'),
    ('TG1305','ICT2122','2025-04-05','14:30:00',2,'Theory','Present');

CREATE TABLE medical(
                        Medi_id INT PRIMARY KEY AUTO_INCREMENT,
                        Me_stu_id CHAR(8) NOT NULL,
                        Me_cou_id CHAR(8) NOT NULL,
                        Lec_type ENUM('Theory', 'Practical'),
                        Reason VARCHAR(250) NOT NULL,
                        Request_date DATE,
                        Status_ ENUM('pending','approved','rejected'),
                        Submitted_date DATE,
                        FOREIGN KEY (Me_stu_id) REFERENCES userAccount(user_id) ,
                        FOREIGN KEY (Me_cou_id) REFERENCES courseUnit(courseId) 
);

INSERT INTO medical (Medi_id, Me_stu_id, Me_cou_id, Lec_type, Reason, Request_date, Status_, Submitted_date)
VALUES
    (1, 'TG1301', 'ICT1233','Theory', 'Fever and flu for two days', '2025-04-20', 'pending', '2025-04-21');

INSERT INTO medical (Me_stu_id, Me_cou_id, Lec_type, Reason, Request_date, Status_, Submitted_date)
VALUES
    ('TG1304', 'ICT2122', 'Theory', 'Fever and flu for two days', '2025-04-05',  'approved','2025-04-21'),
    ('TG1305', 'ICT1234', 'Theory','Surgery recovery', '2025-04-18','pending', '2025-04-20'),
    ('TG1304', 'ICT1240', 'Theory', 'Family emergency during lectures', '2025-04-17',  'pending','2025-04-20'),
    ('TG1302', 'ICT2113', 'Theory','Accident – unable to attend', '2025-04-25',  'approved','2025-04-18'),
    ('TG1301', 'ICT1236', 'Theory','Hospitalized for dengue', '2025-04-15', 'rejected','2025-04-18');
