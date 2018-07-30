Drop Database If Exists ForumOWP;
Create Database ForumOWP;
    
Use ForumOWP;
    
Drop Table If Exists ForumUser;
Create Table ForumUser(
	Id BigInt Not Null Primary Key Auto_Increment,
    UserName VarChar(40) Not Null,
    UserPassword VarChar(128) Not Null,
    FirstName VarChar(30),
    Surname VarChar(40),
    Email VarChar(50) Not Null,
    Disabled Boolean Default False,
    Role VarChar(13) Default 'User',
    DateCreated DateTime Not Null,
    Deleted Boolean Default False
);

Drop Table If Exists Forum;
Create Table Forum(
	Id BigInt Not Null Primary Key Auto_Increment,
    ForumName VarChar(40) Not Null,
    Description VarChar(512),
    Locked Boolean Default False,
    ForumType VarChar(6) Default 'Public',
    DateCreated DateTime Not Null,
	ParentForum BigInt,
    ForumOwner BigInt Not Null,
    Foreign Key (ParentForum) References Forum(Id),
    Foreign Key (ForumOwner) References ForumUser(Id),
    Deleted Boolean Default False
);

Drop Table If Exists Thread;
Create Table Thread(
	Id BigInt Not Null Primary Key Auto_Increment,
    ThreadName VarChar(40) Not Null,
    Description VarChar(255),
    ThreadOwner BigInt Not Null,
    Pinned Boolean Default False,
    Locked Boolean Default False,
    ParentForum BigInt Not Null,
    DateCreated DateTime Not Null,
    Deleted Boolean Default False,
    Foreign Key (ThreadOwner) References ForumUser(Id),
    Foreign Key (ParentForum) References Forum(Id)
);

Drop Table If Exists Post;
Create Table Post(
	Id BigInt Not Null Primary Key Auto_Increment,
    Content VarChar(255) Not Null,
    PostOwner BigInt Not Null,
    ParentThread BigInt Not Null,
    DateCreated DateTime Not Null,
    Foreign Key (PostOwner) References ForumUser(Id),
    Foreign Key (ParentThread) References Thread(Id),
    Deleted Boolean Default False
);

Insert Into ForumUser(UserName, UserPassword, FirstName, Surname, Email, DateCreated)
	Values('uros', 'u', 'Uros', 'Urosevic', 'uu@email.com', '2017-01-31 00:00:00');
Insert Into ForumUser(UserName, UserPassword, FirstName, Surname, Email, Role, DateCreated)
	Values('mika', 'm', 'Mika', 'Mikic', 'mm@email.com', 'Moderator',  '2017-01-31 00:00:00');
Insert Into ForumUser(UserName, UserPassword, FirstName, Surname, Email, Role, DateCreated)
	Values('andrej', 'a', 'Andrej', 'Andrejevic', 'aa@email.com', 'Administrator', '2017-01-31 00:00:00');
    
Insert Into Forum(ForumName, Description, DateCreated, ForumOwner)
	Values('Forum 1', 'Ovo je javni forum', '2017-01-31 00:00:00', 3);
Insert Into Forum(ForumName, Description, ForumType, DateCreated, ForumOwner)
	Values('Forum 2', 'Ovo je otvoreni forum', 'Open', '2017-02-02 00:00:00', 3);
Insert Into Forum(ForumName, Description, DateCreated, ForumOwner, ForumType)
	Values('Forum 3', 'Ovo je zatvoreni forum', '2017-02-24 00:00:00', 3, 'Closed');
Insert Into Forum(ForumName, Description, DateCreated, ForumOwner, ParentForum)
	Values('Forum 4', 'Ovaj forum je podforum foruma 1', '2017-02-09 00:00:00', 3, 1);
Insert Into Forum(ForumName, Description, DateCreated, ForumOwner, ParentForum, ForumType)
	Values('Forum 5', 'Ovaj forum je podforum foruma 2', '2017-02-09 00:00:00', 3, 2, 'Open');
Insert Into Forum(ForumName, Description, DateCreated, ForumOwner, ParentForum, ForumType)
	Values('Forum 6', 'Ovaj forum je podforum foruma 3', '2017-02-09 00:00:00', 3, 3, 'Closed');

    
Insert Into Thread(ThreadName, Description, ThreadOwner, ParentForum, DateCreated)
	Values('Korisnikova tema', 'Ova tema pripada forumu 1', 1, 1, '2017-02-13 00:00:00');
Insert Into Thread(ThreadName, Description, ThreadOwner, ParentForum, DateCreated)
	Values('Moderatorova tema', 'Ova tema pripada forumu 1', 2, 1, '2017-02-15 00:01:00');
Insert Into Thread(ThreadName, Description, ThreadOwner, ParentForum, DateCreated)
	Values('Administratorova tema', 'Ova tema pripada forumu 1', 3, 1, '2017-02-17 00:02:00');
Insert Into Thread(ThreadName, Description, ThreadOwner, ParentForum, DateCreated)
	Values('Korisnikova tema', 'Ova tema pripada forumu 2', 1, 2, '2017-02-13 00:00:00');
Insert Into Thread(ThreadName, Description, ThreadOwner, ParentForum, DateCreated)
	Values('Moderatorova tema', 'Ova tema pripada forumu 2', 2, 2, '2017-02-16 00:01:00');
Insert Into Thread(ThreadName, Description, ThreadOwner, ParentForum, DateCreated)
	Values('Administratorova tema', 'Ova tema pripada forumu 2', 3, 2, '2017-02-18 02:00:00');
Insert Into Thread(ThreadName, Description, ThreadOwner, ParentForum, DateCreated)
	Values('Korisnikova tema', 'Ova tema pripada forumu 3', 1, 3, '2017-02-14 00:00:00');
Insert Into Thread(ThreadName, Description, ThreadOwner, ParentForum, DateCreated)
	Values('Moderatorova tema', 'Ova tema pripada forumu 3', 2, 3, '2017-02-19 00:01:00');
Insert Into Thread(ThreadName, Description, ThreadOwner, ParentForum, DateCreated)
	Values('Administratorova tema', 'Ova tema pripada forumu 3', 3, 3, '2017-02-21 00:02:00');
    
Insert Into Post(Content, PostOwner, ParentThread, DateCreated)
	Values('Ovaj post pripada korisnikovoj temi foruma 1', 1, 1, '2017-02-23 00:02:10');
Insert Into Post(Content, PostOwner, ParentThread, DateCreated)
	Values('Ovaj post pripada korisnikovoj temi foruma 2', 1, 4, '2017-02-25 00:03:15');