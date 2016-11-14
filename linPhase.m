% N = 500;
% n = 0:(N-1);
% 
% sig = cos(2*pi*0.1*n)  + cos(2*pi*0.3*n);
% %sig = ones(1,N);
% %sig = n;
% 
% figure(1)
% stem(n/N, abs(fft(sig)))
% grid on
% 
% M = 40;
% m = 0:(M-1);
% filt = 2 * 0.4 * sinc(0.4 * (m));
% %filt = [ones(1, 5), zeros(1, 10)];
% gFilt = [fliplr(filt(2:M)), filt];
% 
% figure(2)
% plot(gFilt) 
% %plot(m/M, gFilt) 
% %plot(m/M, abs(fft(filt))) 
% grid on

function filtSig = linPhase(sig, filt)
	filtLen = length(filt);
	filtSig = zeros(1, length(sig)+2*filtLen-2);
	sigP = [zeros(1, 2*filtLen-2), sig, zeros(1, 2*filtLen-2)];
	for n = 1:length(filtSig)
		filtSig(n) = sum(((fliplr([sigP(n:(n+filtLen-2)), 0])) + ...
		  sigP((n+filtLen-1):(n+2*filtLen-2))).*filt);
	end %for
end %function

% figure(3)
% plot(filtSig)
% hold on
% plot(conv(sig, gFilt), 'r');
% hold off
% grid on
% 
% figure(4)
% stem(abs(fft(filtSig)))
% hold on
% plot(abs(fft(conv(sig, gFilt))), 'r')
% hold off
% grid on
