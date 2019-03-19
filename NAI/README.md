## Spis Treœci
+ [Zadanie 1 - Tabela pañstwa](#zadanie-1---tabela-pañstw)
+ [Zadanie 2 - Delta algorithm](#zadanie-2---delta-algorithm)

---

## Zadanie 1 - Tabela pañstw
## Zadanie 2 - Delta algorithm



Program wczytuje dane z plików tekstowych. Zak³adamy, ze:
• Pierwszy argument programu to plik z danymi treningowymi, drugi z danymi testowymi.
• Trzeci argument programyu to atrybut decyzyjnt, który chcemy rozró¿niaæ od pozosta³ych.
• Atrybut decyzyjny znajduje siê w ostatniej kolumnie w plikach tekstowych.
• Wszystkie atrybuty poza decycyjnym s¹ numeryczne.
Nastêpnie algorytmem delty trenujemy perceptron, który rozró¿nia klasê podan¹ jako trzeci argument pozosta³ych klas (na danych treningowych).
Testujemy go potem na danych ze zbioru testowego.
Jako wynik wypisuje liczbê prawid³owo zaklasyfikowanych przyk³adów oraz dok³adnoœæ eksperymentu wyra¿on¹ w procentach.
Program umo¿liwia wielokrotne rêczne wpisanie wektora atrybutów i wypisuje dla takiego wektora czy nale¿y do danej klasy.