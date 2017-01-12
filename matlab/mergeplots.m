% Merge plots BandWidth
bandWidthOne = hgload('~/edu/5DV153/assignments/project/matlab/Bandwidth/valve/BandwidthStats1prim1peer.fig');
bandWidthTwo = hgload('~/edu/5DV153/assignments/project/matlab/Bandwidth/valve/BandwidthStatsIP_f2c56c9d-9c8d-4f29-a13f-4d8463725974.fig');
bandWidthThree = hgload('~/edu/5DV153/assignments/project/matlab/Bandwidth/valve/BandwidthStatsIP_eaab5754-2e45-41db-8fbf-5a51f4fde6f9.fig');

figure
h(1)=subplot(2,2,[1 2]);
h(2)=subplot(2,2,3);
h(3)=subplot(2,2,4);


copyobj(allchild(get(bandWidthOne,'CurrentAxes')),h(1));
copyobj(allchild(get(bandWidthTwo,'CurrentAxes')),h(2));
copyobj(allchild(get(bandWidthThree,'CurrentAxes')),h(3));

l(1)=legend(h(1),'LegendForFirstFigure');
l(2)=legend(h(2),'LegendForSecondFigure');
l(2)=legend(h(3),'LegendForSecondFigure');