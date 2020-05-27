%{
If there were two seperate files:
customers = readmatrix('customers.csv');
company = readmatrix('company.csv');
%}

%%Reading Files

A = readmatrix('test.csv');
customers = A(:,1);
[m, n] = size(customers);
customers = reshape(customers, [n, m]);
company = A(:,2);
[m, n] = size(company);
company = reshape(company, [n, m]);

%% Analysis of Customers data
%% Chi - square test
%% Confidence intervals
[conf_a, conf_b] = confintervals(customers, 0.05);
%% Analysis of Company data
%% Chi - square test
%% Confidence intervals
[comp_a, comp_b] = confintervals(company, 0.05);