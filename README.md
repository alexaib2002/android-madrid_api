# Madrid Data API Client

This project samples how to implement data retrieval and formatting from a public API, by using
Retrofit for querying and heavily emphasising on a functional programming approach, while keeping
good practices. Modularity is also implemented, by using Fragments and a container on the main view.

The client can display information publicly available about institutions of Madrid, and allows the
user to choose between a list form (made with Recycler View) or a map (made with Google Maps).

Users can also apply a search filter for searching the institutions of an area with a simple 
FragmentDialog for indicating latitude and longitude, and the radius distance as well of the 
search zone.

## Screenshots

Main activity (with filters) | List fragment | Map fragment | Detail activity
:-:|:-:|:-:|:-:
![image](https://user-images.githubusercontent.com/66980937/222486761-1cfec4dc-1cac-4f28-8d06-b9faa14a0f15.png) | ![image](https://user-images.githubusercontent.com/66980937/222486831-9cb50929-8718-49dc-b01b-acb23d381cd6.png) | ![image](https://user-images.githubusercontent.com/66980937/222486892-fc572b74-c355-4f5e-b754-764764c617d1.png) | ![image](https://user-images.githubusercontent.com/66980937/222486963-2d87a650-0c80-4147-a268-d6a0be299b95.png)
