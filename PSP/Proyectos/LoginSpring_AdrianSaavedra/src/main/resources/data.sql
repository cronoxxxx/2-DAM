
DELETE FROM user_favorite_players;
DELETE FROM credentials;
DELETE FROM players;
DELETE FROM roles;


INSERT INTO roles (rol) VALUES
                            ('USER'),
                            ('ADMIN');


INSERT INTO players (name, team, country) VALUES
                                              ('Kylian Mbappe', 'Real Madrid', 'France'),
                                              ('Erling Haaland', 'Manchester City', 'Norway'),
                                              ('Vinicius Jr', 'Real Madrid', 'Brazil'),
                                              ('Phil Foden', 'Manchester City', 'England'),
                                              ('Jude Bellingham', 'Real Madrid', 'England'),
                                              ('Jamal Musiala', 'Bayern Munich', 'Germany'),
                                              ('Bukayo Saka', 'Arsenal', 'England'),
                                              ('Pedri', 'Barcelona', 'Spain'),
                                              ('Gavi', 'Barcelona', 'Spain'),
                                              ('Rodrygo', 'Real Madrid', 'Brazil'),
                                              ('Alphonso Davies', 'Bayern Munich', 'Canada'),
                                              ('Trent Alexander-Arnold', 'Liverpool', 'England'),
                                              ('Federico Valverde', 'Real Madrid', 'Uruguay'),
                                              ('Bruno Fernandes', 'Manchester United', 'Portugal'),
                                              ('Kevin De Bruyne', 'Manchester City', 'Belgium'),
                                              ('Joshua Kimmich', 'Bayern Munich', 'Germany'),
                                              ('Ruben Dias', 'Manchester City', 'Portugal'),
                                              ('Virgil van Dijk', 'Liverpool', 'Netherlands'),
                                              ('Mohamed Salah', 'Liverpool', 'Egypt'),
                                              ('Harry Kane', 'Bayern Munich', 'England'),
                                              ('Neymar Jr', 'Santos FC', 'Brazil'),
                                              ('Lionel Messi', 'Inter Miami', 'Argentina'),
                                              ('Cristiano Ronaldo', 'Al Nassr', 'Portugal'),
                                              ('Robert Lewandowski', 'Barcelona', 'Poland'),
                                              ('Karim Benzema', 'Al-Ittihad', 'France'),
                                              ('Thibaut Courtois', 'Real Madrid', 'Belgium'),
                                              ('Alisson Becker', 'Liverpool', 'Brazil'),
                                              ('Ederson Moraes', 'Manchester City', 'Brazil'),
                                              ('N''Golo Kante', 'Al-Ittihad', 'France'),
                                              ('Bernardo Silva', 'Manchester City', 'Portugal');


INSERT INTO credentials (username, password, email, is_verified, role_id) VALUES
    ('admin', '$2a$10$pfXTh3lyrCDoD4oE5/J2quQ6Cf0UmpNRDqh54bJ33e1L7lUnpjpU2',
     'admin@example.com', true, (SELECT id FROM roles WHERE rol = 'ADMIN'));

