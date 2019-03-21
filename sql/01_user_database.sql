CREATE USER sl_user WITH PASSWORD 'sl_user_pass';
CREATE DATABASE shopping_list WITH OWNER = sl_user;
GRANT ALL PRIVILEGES ON DATABASE shopping_list TO sl_user;