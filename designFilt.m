M = 1025;
m = (-(M-1)/2):((M-1)/2);

%f_c = 2/M
%f_s = 21/M

f_c = 0.05;
f_s = 0.25;

filt = sinc(m) - 4*f_c*sinc(2*f_c*m) .* cos(2*pi*f_s*m);
filt = 4*f_c*sinc(2*f_c*m) .* cos(2*pi*f_s*m);

%f_l = f_s-f_c;
%f_h = 0.5-f_s-f_c;
%filt = 2*f_l*sinc(2*f_l*m) + 2*f_h*sinc(2*f_h*m) .* (-1).^(m+(M-1)/2); 

beta = 2.87;
win = besseli(0, beta*sqrt(1-(2*m/(M-1)).^2), 0)./besseli(0, beta, 0);
%win = 0.54 + 0.46*cos(2*pi*m);

winfilt = win.*filt;
winfilt_f = fft(winfilt);

figure(1)
plot(m, winfilt, 'linewidth', 1)
title('Filter Coefficients', 'fontsize', 14)
xlabel('m', 'fontsize', 12)
grid on

%print './report/fig_coef.eps' -depsc
%print './report/fig_coef.png' -dpng

sum(winfilt - fliplr(winfilt))
max(abs(fft(winfilt)))

figure(2)

subplot(2, 1, 2)
plot((m+(M-1)/2)/M, 10*log10(abs(winfilt_f)), 'linewidth', 1)
hold on
plot([0.19, 0.19, 0.31, 0.31 ], ...
  [-20, 10*log10(1.02), 10*log10(1.02), -20], ':k')
plot([0.21, 0.21, 0.29, 0.29 ], ...
  [-20, 10*log10(0.98), 10*log10(0.98), -20], ':k')
plot([0, 0.5], [10*log10(0.02), 10*log10(0.02)], ':k')
hold off
axis([0, 0.5, -24, 1])
xlabel('omega (radians)', 'fontsize', 12)
ylabel('Attenuation (dB)', 'fontsize', 12)


subplot(2, 1, 1)
plot((m+(M-1)/2)/M, abs(winfilt_f), 'linewidth', 1)
hold on
plot([0.19, 0.19, 0.31, 0.31 ], ...
  [0, 1.02, 1.02, 0], ':k')
plot([0.21, 0.21, 0.29, 0.29 ], ...
  [0, 0.98, 0.98, 0], ':k')
hold off
axis([0, 0.5, 0.8, 1.1])
title('Impulse Response', 'fontsize', 14)
xlabel('omega (radians)', 'fontsize', 12)
ylabel('Attenuation (V/V)', 'fontsize', 12)

%print './report/fig_ver.eps' -depsc
%print './report/fig_ver.png' -dpng
