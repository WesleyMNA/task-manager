export default function arrayToJson(links: Array<Record<string, string>>): Record<string, string> {
  return links
    .map((link) => ({ [link.rel]: link.href }))
    .reduce((ref, href) => ({ ...ref, ...href }), {});
}
