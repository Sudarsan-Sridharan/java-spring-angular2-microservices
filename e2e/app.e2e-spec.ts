import { WepollPage } from './app.po';

describe('wepoll App', function() {
  let page: WepollPage;

  beforeEach(() => {
    page = new WepollPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
