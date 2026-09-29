const collator = new Intl.Collator('ru', { numeric: true, sensitivity: 'base' });

export function selectTableRows(items, columns, { filterColumn, filterValue, sortColumn, sort, page, pageSize }) {
  const filterValueOf = columns.find((column) => column.key === filterColumn)?.value;
  const sortValueOf = columns.find((column) => column.key === sortColumn)?.value;

  const filtered = filterValue && filterValueOf
    ? items.filter((item) => String(filterValueOf(item) ?? '') === filterValue)
    : items;
  const sorted = sortValueOf ? [...filtered].sort((left, right) => {
    const comparison = collator.compare(String(sortValueOf(left) ?? ''), String(sortValueOf(right) ?? ''));
    return (sort === 'desc' ? -comparison : comparison) || left.id - right.id;
  }) : filtered;

  return {
    total: sorted.length,
    pageCount: Math.max(1, Math.ceil(sorted.length / pageSize)),
    rows: sorted.slice(page * pageSize, (page + 1) * pageSize),
  };
}
