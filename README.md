$path = ".\\database\\schema.sql"



$content = @'

CREATE DATABASE IF NOT EXISTS jewelry\_db;

USE jewelry\_db;



CREATE TABLE IF NOT EXISTS products (

&#x20;   id BIGINT AUTO\_INCREMENT PRIMARY KEY,

&#x20;   product\_name VARCHAR(255) NOT NULL,

&#x20;   category VARCHAR(255),

&#x20;   description TEXT,

&#x20;   price DOUBLE,

&#x20;   weight DOUBLE,

&#x20;   stock INT

);



CREATE TABLE IF NOT EXISTS users (

&#x20;   id BIGINT AUTO\_INCREMENT PRIMARY KEY,

&#x20;   name VARCHAR(255) NOT NULL,

&#x20;   email VARCHAR(255) NOT NULL UNIQUE,

&#x20;   password VARCHAR(255) NOT NULL

);

'@



$utf8NoBom = New-Object System.Text.UTF8Encoding($false)

\[System.IO.File]::WriteAllText((Join-Path (Get-Location) $path), $content, $utf8NoBom)

