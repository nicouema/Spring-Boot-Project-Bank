INSERT INTO user (user_id, email, password, role_id, is_active, created_at)
VALUES(1, "admin1@bank.com", "$2a$10$KqcUV6vcV5GRkNCYgHxMKuKL69z270Ab4gMG5iLQEmp8VvwvckQVm", 1, true, CURRENT_TIMESTAMP),
(1, "user1@bank.com", "$2a$10$jGSx69E3UbeUUMMZvb2tF.dNyuGwab8vV1Oqgpx8JupTN.hNegsXm", 2, true, CURRENT_TIMESTAMP)