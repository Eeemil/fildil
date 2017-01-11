delimiterIn = ' ';
headerlinesIn = 1;
% Read and plot QoS 
% A = importdata('~/edu/5DV153/assignments/project/matlab/QoS/valve/QoSStats_f2c56c9d-9c8d-4f29-a13f-4d8463725974',delimiterIn,headerlinesIn);

% ------UnOrdered QoS-------
id = A.data(:,1);
time = A.data(:,2);


% plot(id(1:length(id)-1),time(1:length(time)-1));

% ------Ordered QoS-------
sortedTime = sort(time(1:length(time)-1));


% plot((1:length(sortedTime)),sortedTime);
fiftyPrctile = prctile(sortedTime,50);
ninetyfivePrctile = prctile(sortedTime,95);
ninetyninePrctile = prctile(sortedTime,99);

dim = [0.2 0.5 0.3 0.3];
strFifty = num2str(fiftyPrctile);
prctileStr = {strcat('Median: ',num2str(fiftyPrctile)), strcat('95 percentile: ', num2str(ninetyfivePrctile)),strcat('99 percentile: ', num2str(ninetyninePrctile))};
% annotation('textbox',dim,'String',prctileStr,'FitBoxToText','on');

%---- bandwidth-----
A = importdata('~/edu/5DV153/assignments/project/matlab/Bandwidth/valve/BandwidthStatsIP_f2c56c9d-9c8d-4f29-a13f-4d8463725974',delimiterIn,headerlinesIn);
time = A.data(:,1);
speed = A.data(:,2);

 plot(time,speed);

