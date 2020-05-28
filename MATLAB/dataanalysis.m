%{
If there were two seperate files:
A = readmatrix('test.csv');
customers = A(:,1);
[m, n] = size(customers);
customers = reshape(customers, [n, m]);
company = A(:,2);
[m, n] = size(company);
company = reshape(company, [n, m]);
%}

%%Reading Files

customers = readmatrix('consumers.csv');
[m_cus, n] = size(customers);
customers = reshape(customers, [n, m_cus]);
company = readmatrix('corporate.csv');
[m_com, n] = size(company);
company = reshape(company, [n, m_com]);


%% Analysis of Customers data
%% Chi - square test
%% Confidence intervals
% At least 90% in 5 minutes
[conf90_a, conf90_b] = confintervals(customers, 0.1);
exceeding = customers(customers>5);
[m, n_ex] = size(exceeding);
perc_5 = n_ex./m_cus;
% At least 95% in 10 minutes
[conf95_a, conf95_b] = confintervals(customers, 0.05);
exceeding = customers(customers>10);
[m, n_ex] = size(exceeding);
perc_10 = n_ex./m_cus;
%% Analysis of Company data
%% Chi - square test
%% Confidence intervals
% At least 95% in 3 minutes
[comp95_a, comp95_b] = confintervals(company, 0.05);
exceeding = company(company>3);
[m, n_ex] = size(exceeding);
perc_3 = n_ex./m_com;
% At least 99% in 7 minutes
[comp99_a, comp99_b] = confintervals(company, 0.01);
exceeding = company(company>7);
[m, n_ex] = size(exceeding);
perc_3 = n_ex./m_com;
