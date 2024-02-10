import pandas as pd
from flask import Flask, jsonify, request
from fyp_pkg import InserimentoDati


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
    ricerca = request.get_json()
    # Eliminare 'idRicerca' e 'dataRicerca', non servono e il modulo non se li aspetta
    ricerca.pop('idRicerca', None)
    ricerca.pop('dataRicerca', None)

    # Mappa i valori di costoVita
    mappa_costo_vita = {'BASSO': 1, 'MEDIO': 2, 'ALTO': 3}

    # Sostituisci 'costoVita' con 1, 2, 3
    if ricerca['costoVita'] in mappa_costo_vita:
        ricerca['costoVita'] = mappa_costo_vita[ricerca['costoVita']]
    elif ricerca['costoVita'] == 'QUALSIASI':
        ricerca['costoVita'] = 3

    # Convertire valori numerici da stringhe a numeri
    ricerca['raggio'] = int(ricerca['raggio'])
    ricerca['dangerMax'] = float(ricerca['dangerMax'])
    ricerca['numAbitantiMin'] = int(ricerca['numAbitantiMin'])
    ricerca['numAbitantiMax'] = int(ricerca['numAbitantiMax'])
    ricerca['numNegoziMin'] = int(ricerca['numNegoziMin'])
    ricerca['numScuoleMin'] = int(ricerca['numScuoleMin'])
    ricerca['numRistorantiMin'] = int(ricerca['numRistorantiMin'])

    # Estrarre i valori per utilizzarli come parametri
    parametri = (
        ricerca['raggio'],
        ricerca['latitude'],
        ricerca['longitude'],
        ricerca['costoVita'],
        ricerca['dangerMax'],
        ricerca['numAbitantiMin'],
        ricerca['numAbitantiMax'],
        ricerca['numNegoziMin'],
        ricerca['numRistorantiMin'],
        ricerca['numScuoleMin']
    )

    dataframe = InserimentoDati.stampa_ricerca(parametri)
    risposta = trasforma_dataframe(dataframe)

    data = jsonify(risposta)
    return data


def trasforma_dataframe(df):
    # verifico se sia effettivamente un dataframe o qualche altro tipo (in errore il modulo restituisce una stringa)
    if df is not isinstance(df, pd.DataFrame):
        return None

    lista_luoghi = []
    for index, row in df.iterrows():
        luogo = {
            'nome': row['Nome'],
            'latitude': row['Latitudine'],
            'longitude': row['Longitudine'],
            'qualityIndex': row['IdQ'],
            'danger': row['Pericolosità'],
            'costoVita': 'ALTO' if row['Costo Vita'] == 2 else 'MEDIO' if row['Costo Vita'] == 1 else 'BASSO',
            'numAbitanti': int(row['Abitanti']),  # Trasformo in intero
            'numNegozi': row['Num Negozi'],
            'numRistoranti': row['Num Ristoranti'],
            'numScuole': row['Num Scuole']
        }
        lista_luoghi.append(luogo)
    return lista_luoghi


if __name__ == '__main__':
    app.run(debug=True)
