function [t_value, p_value, rejected] = unpairedttest(x, y) 
% unpairedttest - Perform an unpaired t-test on two data sets
%
% INPUT:
% x,y <- two data sets that you wish to perfrom the t-test on.
%
% OUTPUT:
% t_value <- test staticts value
% p_value <- p value
% rejected <- if TRUE then the null hypothesis is confirmed
%
    x_mean = mean(x);
    y_mean = mean(y);
    [m, n_x] = size(x);
    [m, n_y] = size(y);
    rejected = false;
    
    % Calculating sample variances
    
    S_x = 0;
    i = 1;
    while i < n_x+1
        S_x = S_x + (x(i)-x_mean)^2;
        i = i +1;
    end
    S_x = S_x ./(n_x-1);
    
    S_y = 0;
    i = 1;
    while i < n_y+1
        S_y = S_y + (y(i)-y_mean)^2;
        i = i +1;
    end
    S_y = S_y ./(n_y-1);
    
    % Degrees of freedom
    
    dof = ((S_x./(n_x-1)) + (S_y./(n_y-1)))^2./((((S_x./(n_x-1))^2)./...
        (n_x-1))+(((S_y./(n_y))^2)./(n_y-1)));
    dof = round(dof);
    
    % test statistic
    
    t_value = (x_mean - y_mean)/sqrt((S_x./(n_x-1))+(S_y./(n_y-1)));
    t_value = abs(t_value);
    
    % p-value
    
    p_value = tcdf(t_value, dof,'upper').*2;
    if p_value < 0.05
        rejected = true;
    end
        
   
end