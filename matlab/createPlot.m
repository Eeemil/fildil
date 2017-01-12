delimiterIn = ' ';
headerlinesIn = 1;

titels = {'1 primary','2 peers ','4 peers'};
UUIDs = {'f2c56c9d-9c8d-4f29-a13f-4d8463725974','4c9b28e8-25bf-42c8-89fa-ca3bf545c4e5'};
srcQoS = '~/edu/5DV153/assignments/project/matlab/QoS/valve/QoSStats_';

importedData(1) = importdata('~/edu/5DV153/assignments/project/matlab/QoS/valve/QoSStats_2c4a82b6-d78d-4e4c-a0b7-23c672176c1f',delimiterIn,headerlinesIn);
importedData(2) = importdata('~/edu/5DV153/assignments/project/matlab/QoS/valve/QoSStats_f2c56c9d-9c8d-4f29-a13f-4d8463725974',delimiterIn,headerlinesIn);
importedData(3) = importdata('~/edu/5DV153/assignments/project/matlab/QoS/valve/QoSStats_4c9b28e8-25bf-42c8-89fa-ca3bf545c4e5',delimiterIn,headerlinesIn);

figure
for i = 1:length(importedData)
    d = importedData(i);
    
    if( i < 3) 
        if(i == 1)
           subplot(2,2,i);
        else
           subplot(2,i,2);
        end
    else
        subplot(2,2,3);
    end
    disp(i);
    id = d.data(:,1);
    time = d.data(:,2);
    plot(id,time);
    title(titels(i));
end

figure
for i = 1:length(importedData)
    d = importedData(i);
    
    if( i < 3) 
        if(i == 1)
           subplot(2,2,i);
        else
           subplot(2,i,2);
        end
    else
        subplot(2,2,3);
    end
    time = sort(d.data(:,2));
    
    plot((1:length(time)),time);
    title(titels(i));
    
    fiftyPrctile = prctile(time,50);
    ninetyfivePrctile = prctile(time,95);
    ninetyninePrctile = prctile(time,99);
    
    dim = [0.2 0.5 0.3 0.3];
    prctileStr = {strcat('Median: ',num2str(fiftyPrctile)), strcat('95 percentile: ', num2str(ninetyfivePrctile)),strcat('99 percentile: ', num2str(ninetyninePrctile))};
    annotation('textbox',dim,'String',prctileStr,'FitBoxToText','on');
end

% ---- Bandwidth ------
importedData(1) = importdata('~/edu/5DV153/assignments/project/matlab/Bandwidth/valve/BandwidthStatsIP_2c4a82b6-d78d-4e4c-a0b7-23c672176c1f',delimiterIn,headerlinesIn);
importedData(2) = importdata('~/edu/5DV153/assignments/project/matlab/Bandwidth/valve/BandwidthStatsIP_f2c56c9d-9c8d-4f29-a13f-4d8463725974',delimiterIn,headerlinesIn);
importedData(3) = importdata('~/edu/5DV153/assignments/project/matlab/Bandwidth/valve/BandwidthStatsIP_4c9b28e8-25bf-42c8-89fa-ca3bf545c4e5',delimiterIn,headerlinesIn);


figure
hold on;
for i = 1:length(importedData)
    d = importedData(i);
    time = d.data(:,1);
    speed = d.data(:,2);
   
    plot(time,speed);
end
title('Bandwidth benchmark'),xlabel('ms'), ylabel('Mbit/s');
hold off;
legend('single primary',titels(2),titels(1));


%figure 

%plot(timeOne,speedOne);
%hold on; 
%plot(timeTwo,speedTwo);
%hold on;
%plot(timeThree,speedThree); 
%title('Bandwidth bencmark'), xlabel('ms'), ylabel('Mbit/s');
%legend('single primary','two peers','four peers');

