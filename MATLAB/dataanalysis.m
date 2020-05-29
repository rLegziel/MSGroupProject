%% Reading Files

customers = readmatrix('consumers3.csv');
[m_cus, n] = size(customers);
customers = reshape(customers, [n, m_cus]);

company = readmatrix('corporate3.csv');
[m_com, n] = size(company);
company = reshape(company, [n, m_com]);

customer_arr = readmatrix('consumers_arr3.csv');
[m,n] = size(customer_arr);
customer_arr = reshape(customer_arr, [n,m]);

company_arr = readmatrix('corporate_arr3.csv');
[m,n] = size(company_arr);
company_arr = reshape(company_arr, [n,m]);

customers_a = readmatrix('consumers1a.csv');
[m_cusa, n] = size(customers_a);
customers_a = reshape(customers_a, [n, m_cusa]);

customers_b = readmatrix('consumers1b.csv');
[m_cusb, n] = size(customers_b);
customers_b = reshape(customers_b, [n, m_cusb]);

company_a = readmatrix('corporate1a.csv');
[m_coma, n] = size(company_a);
company_a = reshape(company_a, [n, m_coma]);

company_b = readmatrix('corporate1.csv');
[m_comb, n] = size(company_b);
company_b = reshape(company_b, [n, m_comb]);


%% Analysis of Customers data
mean_cons = mean(customers)
%% Plot
% plot(customer_arr, customers)
%% Confidence intervals
% At least 90% in 5 minutes
[conf90_a, conf90_b] = confintervals(customers, 0.1);
exceeding = customers(customers>5);
[m, n_ex] = size(exceeding);
perc_5 = 100.*n_ex./m_cus
% At least 95% in 10 minutes
[conf95_a, conf95_b] = confintervals(customers, 0.05)
exceeding = customers(customers>10);
[m, n_ex] = size(exceeding);
perc_10 = 100.*n_ex./m_cus
%% Unpaired t-test
[t,p,r] = unpairedttest(customers_a, customers_b);
%% Analysis of Company data
mean_comp = mean(company)
%% Plot
% plot(company_arr, company)
%% Confidence intervals
% At least 95% in 3 minutes
[comp95_a, comp95_b] = confintervals(company, 0.05)
exceeding = company(company>3);
[m, n_ex] = size(exceeding);
perc_3 = 100.*n_ex./m_com
% At least 99% in 7 minutes
[comp99_a, comp99_b] = confintervals(company, 0.01);
exceeding = company(company>7);
[m, n_ex] = size(exceeding);
perc_7 = 100.*n_ex./m_com
%% Unpaired t-test
[t,p,r] = unpairedttest(company_a, company_b);
