export function getPeriodLabel(date: Date) {
    let month = String(new Date(date).getMonth() + 1);
    let year = new Date(date).getFullYear();
    return year + '-' + month.padStart(2, '0');
  
}
