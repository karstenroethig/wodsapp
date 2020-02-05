-- Create a database for the application to store the data.
create database wodsapp_prod;

-- Create a database user which the application will connect as and grant the required permissions.
create user 'wodsapp_user'@'localhost' identified by 'wodsapp_password';
grant CREATE, DROP, DELETE, INSERT, SELECT, UPDATE, ALTER, REFERENCES on wodsapp_prod.* to 'wodsapp_user'@'localhost';
