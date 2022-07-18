
[QuickDBD-export.pdf](https://github.com/VladislavVas/java-filmorate/files/9129700/QuickDBD-export.pdf)




Films
-
Film_id PK int
Name string
Description string
Duration string
Release_date date

Users
-
User_id PK int
Name string
Login string
Email string
Birthday date

Likes
-
film_id int FK >- Films.Film_id
user_id int FK >- Users.User_id

Rate
-
Rate_id PK int
Rate_name string

Film_Rate
-
Film_id int FK >- Films.Film_id
Rate_id int FK >- Rate.Rate_id

Genre
-
Genre_id PK int
Genre_name string

Film_genre
-
Film_id int FK >- Films.Film_id
Genre_id int FK >- Genre.Genre_id

Friends
-
User_id int FK >- Users.User_id
Friend_id int FK >- Users.User_id
Status boolean
