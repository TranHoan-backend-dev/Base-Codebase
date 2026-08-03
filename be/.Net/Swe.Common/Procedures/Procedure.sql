drop table if exists `Candidate`;
create table candidate
(
    candidate_id    varchar(36)      not null
        primary key,
    name            varchar(255)     not null,
    phone           varchar(11)      not null,
    email           varchar(255)     not null,
    hiring_campaign varchar(255)     null,
    hiring_position varchar(255)     not null,
    hiring_round    varchar(255)     null,
    review          varchar(255)     null,
    hiring_at       date             null,
    status          int(1) default 0 not null,
    created_by      varchar(255)     not null,
    created_at      datetime         not null,
    modified_by     varchar(255)     not null,
    modified_at     datetime         not null,
    constraint Email
        unique (email),
    constraint Phone
        unique (phone)
);

INSERT INTO candidate (candidate_id, name, phone, email, hiring_campaign, hiring_position, hiring_round, review,
                       hiring_at, status, created_by, created_at, modified_by, modified_at)
VALUES ('1d5a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e21', 'Nguyen Van A', '0912345678', 'vana@gmail.com',
        'Tuyển dụng Fresher 2024', 'Backend Developer', 'Vòng phỏng vấn', 'Ứng viên tiềm năng', '2024-04-10', 1,
        'Admin', NOW(), 'Admin', NOW()),
       ('2d5a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e22', 'Tran Thi B', '0987654321', 'thib@gmail.com', 'Tuyển dụng Fresher 2024',
        'Frontend Developer', 'Vòng CV', 'Kỹ năng tốt', '2024-04-11', 0, 'Admin', NOW(), 'Admin', NOW()),
       ('3d5a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e23', 'Le Van C', '0901234567', 'vanc@gmail.com', 'Tuyển dụng Fresher 2024',
        'Mobile Developer', 'Vòng Test', 'Cần cải thiện', '2024-04-12', 2, 'Admin', NOW(), 'Admin', NOW()),
       ('4d5a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e24', 'Pham Minh D', '0912233445', 'minhd@gmail.com',
        'Tuyển dụng Senior 2024', 'Project Manager', 'Vòng Final', 'Rất xuất sắc', '2024-04-13', 1, 'Admin', NOW(),
        'Admin', NOW()),
       ('5d5a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e25', 'Hoang Thanh E', '0922334455', 'thanhe@gmail.com',
        'Tuyển dụng Fresher 2024', 'UI/UX Designer', 'Vòng CV', 'Sáng tạo', '2024-04-14', 0, 'Admin', NOW(), 'Admin',
        NOW()),
       ('6d5a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e26', 'Vuong Quoc F', '0933445566', 'quocf@gmail.com',
        'Tuyển dụng Intern 2024', 'DevOps Engineer', 'Vòng phỏng vấn', 'Nhiệt tình', '2024-04-15', 1, 'Admin', NOW(),
        'Admin', NOW()),
       ('7d5a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e27', 'Dang Thu G', '0944556677', 'thug@gmail.com', 'Tuyển dụng Intern 2024',
        'QA Engineer', 'Vòng Test', 'Cẩn thận', '2024-04-16', 0, 'Admin', NOW(), 'Admin', NOW()),
       ('8d5a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e28', 'Bui Xuan H', '0955667788', 'xuanh@gmail.com',
        'Tuyển dụng Fresher 2024', 'Backend Developer', 'Vòng CV', 'Tư duy tốt', '2024-04-17', 2, 'Admin', NOW(),
        'Admin', NOW()),
       ('9d5a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e29', 'Do Manh I', '0966778899', 'manhi@gmail.com', 'Tuyển dụng Senior 2024',
        'Frontend Developer', 'Vòng Final', 'Kinh nghiệm dày dặn', '2024-04-18', 1, 'Admin', NOW(), 'Admin', NOW()),
       ('105a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e30', 'Phan Hoai J', '0977889900', 'hoaij@gmail.com',
        'Tuyển dụng Intern 2024', 'Data Analyst', 'Vòng phỏng vấn', 'Thông minh', '2024-04-19', 0, 'Admin', NOW(),
        'Admin', NOW()),
       ('115a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e31', 'Ngo Thanh K', '0988990011', 'thanhk@gmail.com',
        'Tuyển dụng Fresher 2024', 'Backend Developer', 'Vòng phỏng vấn', 'Kỹ thuật tốt', '2024-04-20', 1, 'Admin',
        NOW(), 'Admin', NOW()),
       ('125a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e32', 'Ly Van L', '0999001122', 'vanl@gmail.com', 'Tuyển dụng Fresher 2024',
        'Frontend Developer', 'Vòng CV', 'Ham học hỏi', '2024-04-21', 0, 'Admin', NOW(), 'Admin', NOW()),
       ('135a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e33', 'Dinh Xuan M', '0900112233', 'xuanm@gmail.com',
        'Tuyển dụng Intern 2024', 'Mobile Developer', 'Vòng Test', 'Khá tốt', '2024-04-22', 2, 'Admin', NOW(), 'Admin',
        NOW()),
       ('145a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e34', 'Ma Van N', '0911223344', 'vann@gmail.com', 'Tuyển dụng Senior 2024',
        'Fullstack Developer', 'Vòng Final', 'Đa năng', '2024-04-23', 1, 'Admin', NOW(), 'Admin', NOW()),
       ('155a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e35', 'Truong Thu O', '0922334466', 'thuo@gmail.com',
        'Tuyển dụng Fresher 2024', 'UI/UX Designer', 'Vòng CV', 'Mắt thẩm mỹ tốt', '2024-04-24', 0, 'Admin', NOW(),
        'Admin', NOW()),
       ('165a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e36', 'Trinh Van P', '0933445577', 'vanp@gmail.com', 'Tuyển dụng Intern 2024',
        'DevOps Engineer', 'Vòng phỏng vấn', 'Nắm vững kiến thức', '2024-04-25', 1, 'Admin', NOW(), 'Admin', NOW()),
       ('175a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e37', 'Ao Quang Q', '0944556688', 'quangq@gmail.com',
        'Tuyển dụng Intern 2024', 'QA Engineer', 'Vòng Test', 'Tỉ mỉ', '2024-04-26', 0, 'Admin', NOW(), 'Admin', NOW()),
       ('185a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e38', 'Kieu Thi R', '0955667799', 'thir@gmail.com', 'Tuyển dụng Fresher 2024',
        'Backend Developer', 'Vòng phỏng vấn', 'Logic tốt', '2024-04-27', 2, 'Admin', NOW(), 'Admin', NOW()),
       ('195a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e39', 'Cao Minh S', '0966778800', 'minhs@gmail.com', 'Tuyển dụng Senior 2024',
        'Frontend Developer', 'Vòng Final', 'Chăm chỉ', '2024-04-28', 1, 'Admin', NOW(), 'Admin', NOW()),
       ('205a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e40', 'Duong Hoai T', '0977889911', 'hoait@gmail.com',
        'Tuyển dụng Intern 2024', 'Data Analyst', 'Vòng phỏng vấn', 'Phân tích tốt', '2024-04-29', 0, 'Admin', NOW(),
        'Admin', NOW()),
       ('215a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e41', 'Phung Van U', '0912345601', 'vanu@gmail.com',
        'Tuyển dụng Fresher 2024', 'Backend Developer', 'Vòng phỏng vấn', 'Tiềm năng', '2024-05-01', 1, 'Admin', NOW(),
        'Admin', NOW()),
       ('225a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e42', 'Ta Thi V', '0912345602', 'thiv@gmail.com', 'Tuyển dụng Fresher 2024',
        'Frontend Developer', 'Vòng CV', 'Kỹ năng ổn', '2024-05-02', 0, 'Admin', NOW(), 'Admin', NOW()),
       ('235a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e43', 'Nghiem Xuan W', '0912345603', 'xuanw@gmail.com',
        'Tuyển dụng Fresher 2024', 'Mobile Developer', 'Vòng Test', 'Cần thêm kinh nghiệm', '2024-05-03', 2, 'Admin',
        NOW(), 'Admin', NOW()),
       ('245a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e44', 'Kha Minh X', '0912345604', 'minhx@gmail.com', 'Tuyển dụng Senior 2024',
        'Project Manager', 'Vòng Final', 'Rất phù hợp', '2024-05-04', 1, 'Admin', NOW(), 'Admin', NOW()),
       ('255a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e45', 'Banh Thanh Y', '0912345605', 'thanhy@gmail.com',
        'Tuyển dụng Fresher 2024', 'UI/UX Designer', 'Vòng CV', 'Thẩm mỹ tốt', '2024-05-05', 0, 'Admin', NOW(), 'Admin',
        NOW()),
       ('265a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e46', 'Lieu Quoc Z', '0912345606', 'quocz@gmail.com',
        'Tuyển dụng Intern 2024', 'DevOps Engineer', 'Vòng phỏng vấn', 'Nhanh nhẹn', '2024-05-06', 1, 'Admin', NOW(),
        'Admin', NOW()),
       ('275a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e47', 'Kieu Thu A', '0912345607', 'thua@gmail.com', 'Tuyển dụng Intern 2024',
        'QA Engineer', 'Vòng Test', 'Tỉ mỉ cao', '2024-05-07', 0, 'Admin', NOW(), 'Admin', NOW()),
       ('285a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e48', 'Van Xuan B', '0912345608', 'xuanb@gmail.com',
        'Tuyển dụng Fresher 2024', 'Backend Developer', 'Vòng CV', 'Tư duy logic', '2024-05-08', 2, 'Admin', NOW(),
        'Admin', NOW()),
       ('295a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e49', 'Mac Manh C', '0912345609', 'manhc@gmail.com', 'Tuyển dụng Senior 2024',
        'Frontend Developer', 'Vòng Final', 'Vững chắc', '2024-05-09', 1, 'Admin', NOW(), 'Admin', NOW()),
       ('305a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e50', 'On Hoai D', '0912345610', 'hoaid@gmail.com', 'Tuyển dụng Intern 2024',
        'Data Analyst', 'Vòng phỏng vấn', 'Tốt', '2024-05-10', 0, 'Admin', NOW(), 'Admin', NOW()),
       ('315a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e51', 'Quach Thanh E', '0912345611', 'thanhe1@gmail.com',
        'Tuyển dụng Fresher 2024', 'Backend Developer', 'Vòng phỏng vấn', 'Vững tay nghề', '2024-05-11', 1, 'Admin',
        NOW(), 'Admin', NOW()),
       ('325a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e52', 'Ton Van F', '0912345612', 'vanf@gmail.com', 'Tuyển dụng Fresher 2024',
        'Frontend Developer', 'Vòng CV', 'Tích cực', '2024-05-12', 0, 'Admin', NOW(), 'Admin', NOW()),
       ('335a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e53', 'Giap Xuan G', '0912345613', 'xuang@gmail.com',
        'Tuyển dụng Intern 2024', 'Mobile Developer', 'Vòng Test', 'Đạt yêu cầu', '2024-05-13', 2, 'Admin', NOW(),
        'Admin', NOW()),
       ('345a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e54', 'Bui Van H', '0912345614', 'vanh@gmail.com', 'Tuyển dụng Senior 2024',
        'Fullstack Developer', 'Vòng Final', 'Giỏi', '2024-05-14', 1, 'Admin', NOW(), 'Admin', NOW()),
       ('355a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e55', 'Ninh Thu I', '0912345615', 'thui@gmail.com', 'Tuyển dụng Fresher 2024',
        'UI/UX Designer', 'Vòng CV', 'Ổn', '2024-05-15', 0, 'Admin', NOW(), 'Admin', NOW()),
       ('365a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e56', 'Lam Van J', '0912345616', 'vanj@gmail.com', 'Tuyển dụng Intern 2024',
        'DevOps Engineer', 'Vòng phỏng vấn', 'Rất tiềm năng', '2024-05-16', 1, 'Admin', NOW(), 'Admin', NOW()),
       ('375a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e57', 'Phi Quang K', '0912345617', 'quangk@gmail.com',
        'Tuyển dụng Intern 2024', 'QA Engineer', 'Vòng Test', 'Cẩn thận cao', '2024-05-17', 0, 'Admin', NOW(), 'Admin',
        NOW()),
       ('385a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e58', 'Ha Thi L', '0912345618', 'thil@gmail.com', 'Tuyển dụng Fresher 2024',
        'Backend Developer', 'Vòng phỏng vấn', 'Nhanh nhạy', '2024-05-18', 2, 'Admin', NOW(), 'Admin', NOW()),
       ('395a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e59', 'Uong Minh M', '0912345619', 'minhm@gmail.com',
        'Tuyển dụng Senior 2024', 'Frontend Developer', 'Vòng Final', 'Thành thạo', '2024-05-19', 1, 'Admin', NOW(),
        'Admin', NOW()),
       ('405a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e60', 'Ton Hoai N', '0912345620', 'hoain@gmail.com', 'Tuyển dụng Intern 2024',
        'Data Analyst', 'Vòng phỏng vấn', 'Chuyên nghiệp', '2024-05-20', 0, 'Admin', NOW(), 'Admin', NOW()),
       ('415a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e61', 'Vien Van P', '0912345621', 'vanp1@gmail.com',
        'Tuyển dụng Fresher 2024', 'Backend Developer', 'Vòng phỏng vấn', 'Khá', '2024-05-21', 1, 'Admin', NOW(),
        'Admin', NOW()),
       ('425a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e62', 'Vong Thi Q', '0912345622', 'thiq@gmail.com', 'Tuyển dụng Fresher 2024',
        'Frontend Developer', 'Vòng CV', 'Thử việc', '2024-05-22', 0, 'Admin', NOW(), 'Admin', NOW()),
       ('435a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e63', 'Sung Xuan R', '0912345623', 'xuanr@gmail.com',
        'Tuyển dụng Fresher 2024', 'Mobile Developer', 'Vòng Test', 'Cố gắng', '2024-05-23', 2, 'Admin', NOW(), 'Admin',
        NOW()),
       ('445a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e64', 'Man Minh S', '0912345624', 'minhs1@gmail.com',
        'Tuyển dụng Senior 2024', 'Project Manager', 'Vòng Final', 'Tuyệt vời', '2024-05-24', 1, 'Admin', NOW(),
        'Admin', NOW()),
       ('455a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e65', 'Chiem Thanh T', '0912345625', 'thanht@gmail.com',
        'Tuyển dụng Fresher 2024', 'UI/UX Designer', 'Vòng CV', 'Tốt', '2024-04-25', 0, 'Admin', NOW(), 'Admin', NOW()),
       ('465a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e66', 'Khuong Quoc U', '0912345626', 'quocu@gmail.com',
        'Tuyển dụng Intern 2024', 'DevOps Engineer', 'Vòng phỏng vấn', 'Khá', '2024-04-26', 1, 'Admin', NOW(), 'Admin',
        NOW()),
       ('475a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e67', 'Trang Thu V', '0912345627', 'thuv@gmail.com', 'Tuyển dụng Intern 2024',
        'QA Engineer', 'Vòng Test', 'Ổn', '2024-04-27', 0, 'Admin', NOW(), 'Admin', NOW()),
       ('485a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e68', 'Ung Xuan W', '0912345628', 'xuanw1@gmail.com',
        'Tuyển dụng Fresher 2024', 'Backend Developer', 'Vòng CV', 'Được', '2024-04-28', 2, 'Admin', NOW(), 'Admin',
        NOW()),
       ('495a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e69', 'Mieu Manh X', '0912345629', 'manhx@gmail.com',
        'Tuyển dụng Senior 2024', 'Frontend Developer', 'Vòng Final', 'Khá tốt', '2024-04-29', 1, 'Admin', NOW(),
        'Admin', NOW()),
       ('505a7d3b-9a8c-4b5e-9f8d-7c6b5a4a3e70', 'Nhan Hoai Y', '0912345630', 'hoaiy@gmail.com',
        'Tuyển dụng Intern 2024', 'Data Analyst', 'Vòng phỏng vấn', 'Đạt', '2024-04-30', 0, 'Admin', NOW(), 'Admin',
        NOW());

delimiter //
create procedure Proc_Candidate_Insert(
    in CandidateId_0 varchar(36),
    in Name_0 varchar(255),
    in Phone_0 varchar(11),
    in Email_0 varchar(255),
    in HiringCampaign_0 varchar(255),
    in HiringPosition_0 varchar(255),
    in HiringRound_0 varchar(255),
    in Review_0 varchar(255),
    in HiringAt_0 date,
    in Status_0 integer(1),
    in CreatedBy_0 varchar(255),
    in CreatedAt_0 datetime,
    in ModifiedBy_0 varchar(255),
    in ModifiedAt_0 datetime
)
begin
    INSERT INTO `Candidate` (candidate_id, name, phone, email, hiring_campaign, hiring_position, hiring_round, review,
                             hiring_at, status, created_by, created_at, modified_by, modified_at)
    VALUES (CandidateId_0, Name_0, Phone_0, Email_0, HiringCampaign_0, HiringPosition_0, HiringRound_0,
            Review_0, HiringAt_0, Status_0, CreatedBy_0, CreatedAt_0, ModifiedBy_0, ModifiedAt_0);
end //
delimiter ;

delimiter //
create procedure Proc_Candidate_GetAll()
begin
    select * from candidate order by created_at desc;
end //
delimiter ;

delimiter //
create procedure Proc_Candidate_GetById(
    in p_Id varchar(36)
)
begin
    select * from candidate where candidate_id = p_Id;
end //
delimiter ;

delimiter //
create procedure Proc_Candidate_Delete(
    in p_Id varchar(36)
)
begin
    delete from candidate where candidate_id = p_Id;
end //
delimiter ;

delimiter //

drop procedure if exists Proc_Candidate_Update //
create procedure Proc_Candidate_Update(
    in p_Id varchar(36),
    in Name varchar(255),
    in Phone varchar(11),
    in Email varchar(255),
    in HiringCampaign varchar(255),
    in HiringPosition varchar(255),
    in HiringRound varchar(255),
    in Review varchar(255),
    in HiringAt date,
    in ModifiedBy varchar(255),
    in ModifiedAt datetime
)
begin
    update candidate
    set name            = Name,
        phone           = Phone,
        email           = Email,
        hiring_campaign = HiringCampaign,
        hiring_position = HiringPosition,
        hiring_round    = HiringRound,
        review          = Review,
        hiring_at       = HiringAt,
        modified_by     = ModifiedBy,
        modified_at     = ModifiedAt
    where candidate_id = p_Id;
end //
delimiter ;

delimiter //
create procedure Proc_Candidate_GetFilter(
    in p_Keyword varchar(255),
    in p_Limit int,
    in p_Offset int
)
begin
    select *
    from candidate
    where (p_Keyword is null or p_Keyword = ''
        or name like concat('%', p_Keyword, '%')
        or phone like concat('%', p_Keyword, '%')
        or email like concat('%', p_Keyword, '%')
        or hiring_campaign like concat('%', p_Keyword, '%')
        or hiring_position like concat('%', p_Keyword, '%')
    )
    order by modified_at desc
    limit p_Limit offset p_Offset;
    
    select count(*)
    from candidate
    where (p_Keyword is null or p_Keyword = ''
        or name like concat('%', p_Keyword, '%')
        or phone like concat('%', p_Keyword, '%')
        or email like concat('%', p_Keyword, '%')
        or hiring_campaign like concat('%', p_Keyword, '%')
        or hiring_position like concat('%', p_Keyword, '%')
    );
end //
delimiter ;

delete from candidate where candidate_id = '000990' or candidate_id = '1212121';