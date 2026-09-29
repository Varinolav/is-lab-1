import React, { useEffect, useState } from 'react';

const genres = ['DRAMA', 'ADVENTURE', 'TRAGEDY', 'THRILLER'];

export default function Operations({ request, onChanged, revision }) {
  const [deleteGenre, setDeleteGenre] = useState(genres[0]);
  const [beforeGenre, setBeforeGenre] = useState(genres[0]);
  const [busy, setBusy] = useState('');
  const [error, setError] = useState('');
  const [results, setResults] = useState({});

  useEffect(() => {
    setResults(({ palms, count, withoutOscars, ...mutations }) => mutations);
  }, [revision]);

  async function run(key, path, { mutate = false, confirmation } = {}) {
    if (confirmation && !window.confirm(confirmation)) return;
    setBusy(key);
    setError('');
    try {
      const result = await request(`movie-operations/${path}`, { method: mutate ? 'POST' : 'GET' });
      setResults((current) => mutate ? { [key]: result } : { ...current, [key]: result });
      if (mutate) onChanged();
    } catch (cause) {
      setError(cause.message);
    } finally {
      setBusy('');
    }
  }

  const genreOptions = genres.map((genre) => <option key={genre} value={genre}>{genre}</option>);

  return <section className="operations-panel" aria-label="Специальные операции">
    {error && <div className="message error" role="alert">{error}</div>}

    <div className="operation-row">
      <h2>Удалить один фильм по жанру</h2>
      <div className="operation-controls">
        <select aria-label="Жанр для удаления" value={deleteGenre} onChange={(event) => {
          setDeleteGenre(event.target.value);
          setResults((current) => ({ ...current, deleted: undefined }));
        }}>{genreOptions}</select>
        <button className="button secondary" disabled={Boolean(busy)} onClick={() => run('deleted', `delete-one-by-genre/${deleteGenre}`, {
          mutate: true, confirmation: `Удалить один фильм жанра ${deleteGenre}?`,
        })}>Удалить один</button>
      </div>
      {results.deleted !== undefined && <p className="operation-result" role="status">
        {results.deleted ? `Удалён фильм #${results.deleted.deletedId}.` : 'Фильмов этого жанра нет.'}
      </p>}
    </div>

    <div className="operation-row">
      <h2>Сумма «Золотых пальм»</h2>
      <div className="operation-controls"><button className="button secondary" disabled={Boolean(busy)}
        onClick={() => run('palms', 'golden-palms-sum')}>Рассчитать</button></div>
      {results.palms && <p className="operation-result" role="status">Сумма: {results.palms.sum}</p>}
    </div>

    <div className="operation-row">
      <h2>Количество фильмов с жанром меньше заданного</h2>
      <div className="operation-controls">
        <select aria-label="Граница жанра" value={beforeGenre} onChange={(event) => {
          setBeforeGenre(event.target.value);
          setResults((current) => ({ ...current, count: undefined }));
        }}>{genreOptions}</select>
        <button className="button secondary" disabled={Boolean(busy)}
          onClick={() => run('count', `count-before-genre/${beforeGenre}`)}>Посчитать</button>
      </div>
      {results.count && <p className="operation-result" role="status">Количество: {results.count.count}</p>}
    </div>

    <div className="operation-row">
      <h2>Фильмы без «Оскара»</h2>
      <div className="operation-controls"><button className="button secondary" disabled={Boolean(busy)}
        onClick={() => run('withoutOscars', 'without-oscars')}>Показать фильмы</button></div>
      {Array.isArray(results.withoutOscars) && <div className="operation-result" role="status">
        {results.withoutOscars.length === 0 ? 'Таких фильмов нет.' : <ul className="operation-list">
          {results.withoutOscars.map((movie) => <li key={movie.id}>{movie.name} · #{movie.id}</li>)}
        </ul>}
      </div>}
    </div>

    <div className="operation-row">
      <h2>Добавить по одному «Оскару» фильмам с рейтингом R</h2>
      <div className="operation-controls"><button className="button secondary" disabled={Boolean(busy)}
        onClick={() => run('updated', 'add-oscar-to-r', {
          mutate: true, confirmation: 'Добавить по одному «Оскару» всем фильмам с рейтингом R?',
        })}>Добавить «Оскар»</button></div>
      {results.updated && <p className="operation-result" role="status">Обновлено фильмов: {results.updated.updated}</p>}
    </div>
  </section>;
}
