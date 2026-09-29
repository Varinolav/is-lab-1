import assert from 'node:assert/strict';
import test from 'node:test';
import { selectTableRows } from './tableData.js';

const columns = [
  { key: 'name', value: (item) => item.name },
  { key: 'rating', value: (item) => item.rating },
];
const items = Array.from({ length: 25 }, (_, index) => ({
  id: index + 1,
  name: `Фильм ${String(25 - index).padStart(2, '0')}`,
  rating: index === 24 ? 'R' : 'G',
}));

test('sorts the full list before slicing pages', () => {
  const options = { filterColumn: 'name', filterValue: '', sortColumn: 'name', sort: 'asc', pageSize: 20 };
  const first = selectTableRows(items, columns, { ...options, page: 0 });
  const second = selectTableRows(items, columns, { ...options, page: 1 });
  assert.equal(first.rows.length, 20);
  assert.equal(first.rows[0].name, 'Фильм 01');
  assert.equal(second.rows[0].name, 'Фильм 21');
  assert.equal(second.pageCount, 2);
});

test('filters by exact value in a chosen string column before pagination', () => {
  const result = selectTableRows(items, columns, {
    filterColumn: 'rating', filterValue: 'R', sortColumn: 'name', sort: 'asc', page: 0, pageSize: 10,
  });
  assert.equal(result.total, 1);
  assert.equal(result.rows[0].id, 25);
  assert.equal(selectTableRows(items, columns, {
    filterColumn: 'rating', filterValue: 'r', sortColumn: 'name', sort: 'asc', page: 0, pageSize: 10,
  }).total, 0);
});
