import random
from flask import Flask, jsonify

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://root:password@localhost:3306/findyourplace'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False


@app.route('/search-luoghi', methods=['POST'])
def search_luoghi():
    """
Questa funzione gestisce la richiesta POST per avviare il modulo.

Returns:
    flask.Response: Risposta JSON.
"""
    # Va sostituito con il modulo di IA
    lista_luoghi = [
        {"nome": "Bergamo", "latitude": 45.6944947, "longitude": 9.6698727, "qualityIndex": 66.46404280304223,
         "danger": 20.242155577060352, "costoVita": "ALTO", "numAbitanti": 119809,
         "numNegozi": 498, "numRistoranti": 7987, "numScuole": 268},
        {"nome": "Cuneo", "latitude": 44.389633, "longitude": 7.5479007, "qualityIndex": 58.83000077660779,
         "danger": 16.013278691013483, "costoVita": "MEDIO", "numAbitanti": 55844,
         "numNegozi": 685, "numRistoranti": 24759, "numScuole": 190},
        {"nome": "Genova", "latitude": 44.40726, "longitude": 8.9338624, "qualityIndex": 61.04122606641853,
         "danger": 19.62194826871384, "costoVita": "MEDIO", "numAbitanti": 561191,
         "numNegozi": 4665, "numRistoranti": 191214, "numScuole": 2218},
        {"nome": "Salerno", "latitude": 40.6803601, "longitude": 14.7594542, "qualityIndex": 35.081158079870626,
         "danger": 23.58997117868908, "costoVita": "BASSO", "numAbitanti": 127485,
         "numNegozi": 438, "numRistoranti": 20003, "numScuole": 114},
        {"nome": "Milano", "latitude": 45.4641943, "longitude": 9.1896346, "qualityIndex": 67.92584671698411,
         "danger": 34.29281960751504, "costoVita": "ALTO", "numAbitanti": 1358420,
         "numNegozi": 5262, "numRistoranti": 138791, "numScuole": 1792}
    ]

    return jsonify(lista_luoghi)



if __name__ == '__main__':
    app.run(debug=True)